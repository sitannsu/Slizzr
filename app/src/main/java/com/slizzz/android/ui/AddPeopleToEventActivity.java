package com.slizzz.android.ui;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daprlabs.cardstack.SwipeDeck;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.SwipeDeckAdapter;
import com.slizzz.android.adapter.SwipeDeckUserAdapter;
import com.slizzz.android.model.Event;
import com.slizzz.android.model.User;
import com.slizzz.android.ui.fragment.DashboardFragment;
import com.slizzz.android.util.AddFragments;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 28/08/18.
 */

public class AddPeopleToEventActivity extends AppCompatActivity {



    private Toolbar mToolbar;
    private TextView header, eventName;
    SwipeDeck cardStack;
    Event event;
    List<ParseUser> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_addpeopletoevent);
        event = LocalManager.getInstance().getSelectedEvent();

        setToolBar();

        getUserList();

        //AddFragments.addFragmentToStack(this,null, DashboardFragment.class);


    }




    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        header = (TextView)findViewById(R.id.header);
        header.setText("FIND PEOPLE");

        eventName = (TextView)findViewById(R.id.eventName);
        eventName.setText(event.getName());

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private void getUserList()
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {

                    Log.d("objectsobjects",""+objects.size());
                    // The query was successful.
                    inItUi(objects);
                } else {
                    // Something went wrong.
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void inItUi(List<ParseUser> objects) {



        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(500);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(500);
            }
        });

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        final ArrayList<Event> testData = new ArrayList<>();


        final SwipeDeckUserAdapter adapter = new SwipeDeckUserAdapter(objects, this);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
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


}
