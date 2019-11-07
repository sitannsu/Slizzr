package com.slizzz.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.AttendeAdapter;
import com.slizzz.android.adapter.CHatADapter;
import com.slizzz.android.model.Event;
import com.slizzz.android.ui.ChatActivity;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 26/10/18.
 */

public class ChatFragment extends Fragment implements View.OnClickListener{




    private RecyclerView chatRecycler;
    private View view;
    CHatADapter adapter;
    List<ParseUser>userList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private List<String> eventName = new ArrayList<>();
    List<String>userIdList = new ArrayList<>();
    private LinearLayout eventSPinnerLL;
    private Spinner eventSpinner;
    private Event event;
    private TextView recentChat;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);

        event = LocalManager.getInstance().getSelectedEvent();


        inItUi();

        //getUserList();

        //getEventsData();
        getAttendantEventsData(event);

        return view;

    }


    private void getEventsData() {

        ParseQuery<Event> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("createdBy", event.get("createdBy"));
        query.findInBackground(new FindCallback<Event>() {
            int started = 1;

            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    eventList = list;
                    for(Event event: list)
                    {
                        eventName.add(event.getName());
                    }

                    setSpinner();



                } else
                {

                }
            }
        });

    }

    private void setSpinner() {

        ArrayAdapter<String> dataAdapterflatFacing = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_dropdown_item, eventName);
        eventSpinner.setAdapter(dataAdapterflatFacing);
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getAttendantEventsData(eventList.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAttendantEventsData(Event event) {
        UiUtil.showProgressDialogue(getActivity(),"","Fetching data");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SZEventAttendee");
        query.whereEqualTo("eventID", event.getObjectId());

        query.findInBackground(new FindCallback<ParseObject>() {
            int started = 1;

            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    UiUtil.cancelProgressDialogue();


                    for(ParseObject object: list)
                    {
                        userIdList.add(object.getString("attendedBy"));
                    }

                    getUserList();


                } else
                {

                }
            }
        });

    }




    private void getUserList()
    {
        UiUtil.showProgressDialogue(getActivity(),"","Loading");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn("objectId", userIdList);


        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    userList = objects;

                    UiUtil.cancelProgressDialogue();
                    Log.d("objectsobjects",""+objects.size());
                    adapter.notifyUserChanged(objects);

                } else {
                    // Something went wrong.
                }
            }
        });

    }


    private void inItUi() {
        eventSPinnerLL = (LinearLayout) view.findViewById(R.id.eventSPinnerLL);
        eventSpinner = (Spinner) view.findViewById(R.id.eventSpinner);
        chatRecycler = (RecyclerView)view.findViewById(R.id.chatRecycler);





        chatRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new CHatADapter(getActivity(), new ArrayList<ParseUser>(), this );
        //attendeRecycler.setHasFixedSize(true);
        //attendeRecycler.setNestedScrollingEnabled(false);
        chatRecycler.setAdapter(adapter);

        eventSPinnerLL.setOnClickListener(this);

        recentChat = (TextView) view.findViewById(R.id.recentChat);
        recentChat.setText("Recent Chat for Event : "+event.getName());
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id)
        {
            case R.id.parentLayout:

                int pos = (Integer) view.getTag();
                LocalManager.getInstance().setParseUser(userList.get(pos));

                startActivity(new Intent(getActivity(), ChatActivity.class));

                break;

            case R.id.eventSPinnerLL:

                eventSpinner.performClick();

                break;
        }

    }
}
