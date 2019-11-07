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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.MyAttendingEventListAdapter;
import com.slizzz.android.adapter.MyAttendingEventListAdapter2;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 25/10/18.
 */

public class MyZicketsFragment extends Fragment implements View.OnClickListener{


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View view;

    private RecyclerView myEventRecycler;
    private MyAttendingEventListAdapter2 adapter;
    List<Event> eventList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scanzickets, container, false);


        inItUI();

        getEventsData();
        return view;

    }

    private void inItUI() {


        myEventRecycler = (RecyclerView)view.findViewById(R.id.eventRecycler);
        myEventRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter = new MyAttendingEventListAdapter2(getActivity(), this, eventList );
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                UiUtil.showToast(getActivity(),result.getContents());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.parentLayout:

                IntentIntegrator.forSupportFragment(MyZicketsFragment.this).initiateScan();


                break;
        }

    }
}
