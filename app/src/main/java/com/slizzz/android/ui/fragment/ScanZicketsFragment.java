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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.MyAttendingEventListAdapter;
import com.slizzz.android.adapter.MyAttendingEventListAdapter2;
import com.slizzz.android.model.Event;
import com.slizzz.android.ui.EventDetailsActivity;
import com.slizzz.android.ui.ScanDetailsActivity;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 25/10/18.
 */

public class ScanZicketsFragment extends Fragment implements View.OnClickListener{


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View view;

    private RecyclerView myEventRecycler;
    private MyAttendingEventListAdapter2 adapter;
    List<Event> eventList = new ArrayList<>();
    List<String>attendedEventIdList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scanzickets, container, false);





        inItUI();

        getEventsData();
        return view;

    }

    private void getEventsData() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("SZEventAttendee");
        query.whereEqualTo("attendedBy", ParseUser.getCurrentUser().getObjectId());

        query.findInBackground(new FindCallback<ParseObject>() {
            int started = 1;

            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    UiUtil.cancelProgressDialogue();


                    for(ParseObject object: list)
                    {
                        attendedEventIdList.add(object.getString("eventID"));
                    }

                    getAttendedEventList();


                } else
                {

                }
            }
        });

    }


    private void getAttendedEventList() {

        ParseQuery<Event> query = ParseQuery.getQuery("Event");
        query.whereContainedIn("objectId", attendedEventIdList);
        query.findInBackground(new FindCallback<Event>() {
            int started = 1;

            @Override
            public void done(List<Event> list, ParseException e) {
                if (e == null) {
                    eventList = list;
                    UiUtil.cancelProgressDialogue();
                    adapter.notifyEvetChanged(list);
                    Log.d("EventEvent","" +list.size());
                    for(Event event: list)
                    {



                    }


                } else
                {

                }
            }
        });
    }

    private void inItUI() {


        myEventRecycler = (RecyclerView)view.findViewById(R.id.eventRecycler);
        myEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter = new MyAttendingEventListAdapter2(getActivity(), this, eventList );
        myEventRecycler.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.parentLayout:

                int pos = (Integer) v.getTag();
                LocalManager.getInstance().setSelectedEvent(eventList.get(pos));

                startActivity(new Intent(getActivity(), ScanDetailsActivity.class));




                break;
        }

    }
}
