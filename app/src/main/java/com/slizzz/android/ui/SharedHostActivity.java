package com.slizzz.android.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.slizzz.android.R;
import com.slizzz.android.adapter.AttendeAdapter;
import com.slizzz.android.adapter.SharedHostInviteADapter;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sjena on 07/12/18.
 */

public class SharedHostActivity extends AppCompatActivity implements View.OnClickListener{



    private RecyclerView attendeRecycler;
    private Toolbar mToolbar;
    SharedHostInviteADapter adapter;
    private Event event;
    private TextView header;
    private List<ParseUser> userList = new ArrayList<>();

    private ImageView eventImage;

    private TextView eventName, eventDay, eventMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        event = LocalManager.getInstance().getSelectedEvent();
        setContentView(R.layout.activity_sharehost);
        setToolBar();
        inItUi();

        getUserList();


    }


    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        header = (TextView)findViewById(R.id.header);
        header.setText("ADD SHARED HOSTS");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    private void inItUi() {

        findViewById(R.id.sentMessageRL).setVisibility(View.GONE);


        eventImage = (ImageView) findViewById(R.id.eventImage);
        eventName = (TextView)  findViewById(R.id.eventName);
        eventDay = (TextView)  findViewById(R.id.eventDay);
        eventMonth = (TextView) findViewById(R.id.eventMonth);

        byte [] bitmapdata= new byte[0];
        try {
            bitmapdata = event.getCoverImage().getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventImage.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length));
        eventName.setText(event.getName());

        Calendar c = Calendar.getInstance();
        c.setTime(event.getDate());
        eventDay.setText(""+c.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format1=new SimpleDateFormat("MMM yyyy");


        eventMonth.setText(format1.format(event.getDate()));




        attendeRecycler = (RecyclerView)findViewById(R.id.attendeRecycler);



        attendeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new SharedHostInviteADapter(this, new ArrayList<ParseUser>(), this );
        //attendeRecycler.setHasFixedSize(true);
        //attendeRecycler.setNestedScrollingEnabled(false);
        attendeRecycler.setAdapter(adapter);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adapter.getSelectedPositionList().size() == 0)
                {
                    UiUtil.showToast(SharedHostActivity.this, "Please select a  host" );
                }else
                {
                    addShareddHostToDb();

                }

            }
        });
    }

    private void addShareddHostToDb() {

        List<Integer> userPoslist = adapter.getSelectedPositionList();

           for(int pos : userPoslist) {
               ParseUser user = userList.get(pos);


               ParseObject profilePicture = ParseObject.create("SZSharedHosts");
               UiUtil.showProgressDialogue(this, "", "Adding host");
               profilePicture.put("eventName", event.getName());

               profilePicture.put("eventID", event.getObjectId());
               profilePicture.put("hostedBy", ParseUser.getCurrentUser().getObjectId());
               profilePicture.put("sharedHosts", user);
               profilePicture.put("event", event);

               ParseACL acl = new ParseACL();
               acl.setWriteAccess(ParseUser.getCurrentUser(), true);

               acl.setPublicReadAccess(true);
               acl.setPublicWriteAccess(true);
               profilePicture.put("isPublic", false);

               profilePicture.saveInBackground(new SaveCallback() {
                   @Override
                   public void done(ParseException e) {
                       if (e == null) {
                           Log.i("Parse", "saved successfully");
                           UiUtil.showToast(SharedHostActivity.this, "SharedHost create successfully");

                           finish();

                       } else {
                           Log.i("Parse", "error while saving");
                       }

                       UiUtil.cancelProgressDialogue();
                   }
               });
           }

    }


    private void getUserList()
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();


        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {

                    Log.d("objectsobjects",""+objects.size());
                    adapter.notifyUserChanged(objects);
                    userList = objects;

                } else {
                    // Something went wrong.
                }
            }
        });

    }


    @Override
    public void onClick(View view) {

    }
}
