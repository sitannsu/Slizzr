package com.slizzz.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.MyAttendingEventListAdapter;
import com.slizzz.android.model.Event;
import com.slizzz.android.ui.MyeventDeatilsActivity;
import com.slizzz.android.ui.SharedHostActivity;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 24/08/18.
 */

public class MyEventsFragment  extends Fragment implements View.OnClickListener{



    private View view;
    private RecyclerView myEventRecycler;
    private MyAttendingEventListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_myevents, container, false);


        inItUi();


        getEventsData();

        return view;
    }

    List<Event> eventList = new ArrayList<>();

    private void getEventsData() {

        ParseQuery<Event> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Event>() {
            int started = 1;

            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    eventList = list;
                    UiUtil.cancelProgressDialogue();
                    Log.d("listlistlist",""+list.size());
                    //adapter.notifyDataSetChanged(list);
                    eventList.addAll(list);
                    adapter.notifyEvetChanged(eventList);

                } else
                {

                }
            }
        });

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("SZSharedHosts");
        query3.whereEqualTo("sharedHosts", ParseUser.getCurrentUser());

        ParseQuery<ParseObject> query2 =ParseQuery.getQuery("ProfileViewCounts");
        query2.whereMatchesQuery("objectId", query3);
        query2.getFirstInBackground(new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject object, ParseException e) {

                 Log.d("object",""+object);
            }
        });


/*        query2.findInBackground(new FindCallback<ParseObject>() {
            int started = 1;

            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {

                    for(ParseObject object : list)
                    {
                        Event event = (Event)object.get("event");
                        eventList.add(event);

                    }
                    adapter.notifyEvetChanged(eventList);

                } else
                {

                }
            }
        });*/

    }

    private void getData() throws ParseException {

       /* ParseUser.getCurrentUser();

        ParseQuery<Event> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
        // this will find the user.
        // then find the first instance
        query.getInBackground(new GetCallback<Event>() {
            @Override
            public void done(Event object, ParseException e) {

                if (e == null) {

                    Log.d("objectobject", ""+object );
                } else {

                }


        }}});*/
        ParseQuery<Event> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Event>() {
            public void done(List<Event> itemList, ParseException e) {
                if (e == null) {

                     

                    //Toast.makeText(TodoItemsActivity.this, firstItemId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });


    }

    private void inItUi() {

        myEventRecycler = (RecyclerView)view.findViewById(R.id.myEventRecycler);
        myEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter = new MyAttendingEventListAdapter(getActivity(), this, eventList );
        myEventRecycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.parentLayout:

                int pos = (Integer)v.getTag();
                LocalManager.getInstance().setSelectedEvent(eventList.get(pos));
                Intent intent = new Intent(getActivity(), MyeventDeatilsActivity.class);


                startActivity(intent);


                break;


            case R.id.addSharedHost:
                int pos2 = (Integer)v.getTag();

                LocalManager.getInstance().setSelectedEvent(eventList.get(pos2));
                Intent intent2 = new Intent(getActivity(), SharedHostActivity.class);
                startActivity(intent2);

                break;
        }

    }
}
