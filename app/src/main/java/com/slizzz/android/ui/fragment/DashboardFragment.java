package com.slizzz.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.slizzz.android.R;

import com.slizzz.android.adapter.HeaderAdapter;
import com.slizzz.android.adapter.SwipeDeckAdapter;
import com.slizzz.android.model.Event;
import com.slizzz.android.ui.CreateEventActivity;
import com.slizzz.android.ui.SplashActivity;
import com.slizzz.android.util.UiUtil;
import com.slizzz.android.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 15/08/18.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener{



    private View view;
    SwipeDeck cardStack;
    private RecyclerView headerRecycler;
    private HeaderAdapter headerAdapter;
    private String[] headerTittleList = {"ALL","PREPAID","PAY AT DOOR","FREE"};
    ArrayList<Event> eventList = new ArrayList<>();
    ArrayList<Event> eventListAll = new ArrayList<>();
    ArrayList<Event> prpeaidList = new ArrayList<>();
    ArrayList<Event> freeList = new ArrayList<>();
    ArrayList<Event> payAtdoorList = new ArrayList<>();
    SwipeDeckAdapter adapter;
    int selectedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard, container, false);




        setHeaderRecycler();
        ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser == null)
        {
            //logOutUser();
        }else {
            parseUser.getObjectId();
        }
        Log.d("parseUser", " "+new Gson().toJson(parseUser));

        getData();
        return view;

    }

    private void getData() {







        //ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Event");
        UiUtil.showProgressDialogue(getActivity(),"","Loading data");


        ParseQuery<Event> query = ParseQuery.getQuery("Event");

        query.findInBackground(new FindCallback<Event>() {
            int started = 1;

            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    UiUtil.cancelProgressDialogue();
                    //adapter.notifyDataSetChanged(list);

                    for(Event event: list)
                    {
                        Log.d("parseObjects","parseObjectsparseObjects"+event.getFeeType() );

                        eventList.add(event);
                        if(event.getFeeType() != null && event.getFeeType().equalsIgnoreCase("paidAtDoor"))
                        {
                            payAtdoorList.add(event);
                        }else if(event.getFeeType() != null && event.getFeeType().equalsIgnoreCase("prepaid"))
                        {
                            prpeaidList.add(event);
                        }else
                        {
                            freeList.add(event);
                        }
                    }

                    eventListAll = eventList;
                    inItUi();
                } else
                {

                }
            }
        });

      /*  query.findInBackground(new FindCallback<ParseObject>() {
            int started = 1;

            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    UiUtil.cancelProgressDialogue();
                    //adapter.notifyDataSetChanged(list);

                    for(ParseObject event: list)
                    {
                        Log.d("1111","111");

                    }


                } else
                {

                }
            }
        });*/

    }



    private void logOutUser() {

        ParseUser.getCurrentUser().logOut();
        startActivity(new Intent(getActivity(), SplashActivity.class));
        UiUtil.addLoginToSharedPref(getActivity(), false);
        getActivity().finish();
    }


    private void setHeaderRecycler() {

        headerRecycler = (RecyclerView)view.findViewById(R.id.headerRecycler);
        headerRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        headerAdapter = new HeaderAdapter(getActivity(),headerTittleList, this );
        headerRecycler.setAdapter(headerAdapter);
    }

    private void inItUi() {


        view.findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(500);
            }
        });

        view.findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDb();

            }
        });

        cardStack = (SwipeDeck) view.findViewById(R.id.swipe_deck);




        adapter = new SwipeDeckAdapter(eventListAll, getActivity());
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                selectedPosition = position;
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void cardsDepleted() {
                Log.i("MainActivity", "no more cards");
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });






    }

    private void callDb() {

        ParseObject attendee = new ParseObject("SZEventAttendee");
        UiUtil.showProgressDialogue(getActivity(), "","Loading");
        attendee.put("attendedBy", ParseUser.getCurrentUser().getObjectId());
        attendee.put("name", ParseUser.getCurrentUser().getObjectId());
        attendee.put("descriptionText", eventListAll.get(selectedPosition).getDescriptionText());
        attendee.put("eventID", eventListAll.get(selectedPosition).getObjectId());
        ParseACL acl=new ParseACL();
        acl.setWriteAccess(ParseUser.getCurrentUser(), true);
        attendee.setACL(acl);
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        attendee.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if (e == null)
                {
                    Log.i("Parse", "saved successfully");
                    UiUtil.showToast(getActivity(),"Success");

                    cardStack.swipeTopCardLeft(500);
                    UiUtil.showToast(getActivity(), "You have attended the event");
                }
                else
                {
                    Log.i("Parse", "error while saving");
                    UiUtil.showToast(getActivity(), "Error , please try after some time");
                }


                UiUtil.cancelProgressDialogue();
            }
        });

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.headerName:

                int pos = (Integer)v.getTag();
                headerAdapter.setSelectedPos(pos);
                //viewPager.setCurrentItem(pageIndex);

                //eventList.clear();
                if(pos == 0) {
                    adapter.notifyDataSetChanged(eventList);
                    eventListAll = eventList;
                }else if(pos == 1) {
                    if(adapter != null) {
                        adapter.notifyDataSetChanged(prpeaidList);
                        eventListAll = prpeaidList;
                    }
                }if(pos == 2) {
                if(adapter != null) {
                    adapter.notifyDataSetChanged(payAtdoorList);
                    eventListAll = payAtdoorList;
                }
                  }if(pos == 3) {
                if(adapter != null) {
                    adapter.notifyDataSetChanged(freeList);
                    eventListAll = freeList;
                }
            }
                inItUi();
                cardStack.swipeTopCardLeft(100);

                //cardStack.removeTopCard();
                break;
        }

    }
}
