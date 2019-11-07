package com.slizzz.android.ui;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.AttendeAdapter;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 02/09/18.
 */

public class MyeventDeatilsActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView attendeRecycler;
    private Toolbar mToolbar;
    AttendeAdapter adapter;
    private Event event;
    private TextView header;
    private List<String>userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        event = LocalManager.getInstance().getSelectedEvent();
        setContentView(R.layout.activity_attendingevents);
        setToolBar();
        inItUi();

        getAttendingList();


    }


    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        header = (TextView)findViewById(R.id.header);
        header.setText("ATTENDEES LIST");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private void getAttendingList() {
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
                        userList.add(object.getString("attendedBy"));
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
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn("objectId", userList);

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {

                    Log.d("objectsobjects",""+objects.size());
                    adapter.notifyUserChanged(objects);

                } else {
                    // Something went wrong.
                }
            }
        });

    }




    private void inItUi() {

        attendeRecycler = (RecyclerView)findViewById(R.id.attendeRecycler);



        attendeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new AttendeAdapter(this, new ArrayList<ParseUser>(), this, event );
        //attendeRecycler.setHasFixedSize(true);
        //attendeRecycler.setNestedScrollingEnabled(false);
        attendeRecycler.setAdapter(adapter);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onClick(View v) {

    }
}
