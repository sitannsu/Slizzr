package com.slizzz.android.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.parse.ParseException;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.LocalManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by sjena on 10/09/18.
 */

public class EventDetailsActivity extends AppCompatActivity {

    private TextView header, eventName, eventDay, eventMonth;
    private Toolbar mToolbar;
    private RelativeLayout sentMessageRL;
    private Event event;
    private ImageView eventImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetails);
        event = LocalManager.getInstance().getSelectedEvent();

        header = (TextView)findViewById(R.id.header);
        header.setText("EVENT DETAILS");

        setToolBar();

        inItUi();

    }

    private void inItUi() {

        sentMessageRL = (RelativeLayout)findViewById(R.id.sentMessageRL);
        sentMessageRL.setVisibility(View.GONE);

        eventImage = (ImageView) findViewById(R.id.eventImage);
        byte [] bitmapdata= new byte[0];
        try {
            bitmapdata = event.getCoverImage().getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventImage.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length));

        eventName = (TextView) findViewById(R.id.eventName);
        eventName.setText(event.getName());
        eventDay = (TextView) findViewById(R.id.eventDay);
        Calendar c = Calendar.getInstance();
        c.setTime(event.getDate());
        eventDay.setText(""+c.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format1=new SimpleDateFormat("MMM yyyy");

        eventMonth = (TextView) findViewById(R.id.eventMonth);
        eventMonth.setText(format1.format(event.getDate()));
    }

    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
