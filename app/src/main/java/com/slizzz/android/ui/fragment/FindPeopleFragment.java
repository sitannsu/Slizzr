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
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.FindPeopleAdapter;
import com.slizzz.android.adapter.MyAttendingEventListAdapter;
import com.slizzz.android.adapter.MyEventListAdapter;
import com.slizzz.android.model.Event;
import com.slizzz.android.ui.AddPeopleToEventActivity;
import com.slizzz.android.ui.CreateEventActivity;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 28/08/18.
 */

public class FindPeopleFragment extends Fragment implements View.OnClickListener{


    private View view;
    private RecyclerView myEventRecycler;
    private MyAttendingEventListAdapter adapter;
    List<Event>eventList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_findpeople, container, false);

        inItUI();
        getEventsData();
        return view;
    }

    private void inItUI() {


        myEventRecycler = (RecyclerView)view.findViewById(R.id.myEventRecycler);
        myEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter = new MyAttendingEventListAdapter(getActivity(), this, eventList );
        myEventRecycler.setAdapter(adapter);
    }

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
                    adapter.notifyEvetChanged(list);

                } else
                {

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id)
        {
            case R.id.parentLayout:

                int pos = (Integer) v.getTag();
                LocalManager.getInstance().setSelectedEvent(eventList.get(pos));

                startActivity(new Intent(getActivity(), AddPeopleToEventActivity.class));

                break;
        }

    }
}

