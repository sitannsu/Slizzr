package com.slizzz.android.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;

import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.zxing.WriterException;
import com.parse.ParseException;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.LocalManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Created by sjena on 25/10/18.
 */

public class ScanDetailsActivity extends AppCompatActivity {

    private TextView header, eventName, eventDay, eventMonth, eventDesc;
    private Toolbar mToolbar;
    private RelativeLayout sentMessageRL;
    private Event event;
    private ImageView eventImage, scnImage;

    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetails);
        event = LocalManager.getInstance().getSelectedEvent();

        header = (TextView)findViewById(R.id.header);
        header.setText("EVENT DETAILS");
        eventDesc = (TextView)findViewById(R.id.eventDesc);
        eventDesc.setVisibility(View.GONE);
        findViewById(R.id.scan).setVisibility(View.GONE);

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


        scnImage = (ImageView) findViewById(R.id.scnImage);
        final int visible = View.VISIBLE;
        scnImage.setVisibility(visible);
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        QRGEncoder qrgEncoder = new QRGEncoder("10$", null, QRGContents.Type.TEXT, smallerDimension);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            scnImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
           // Log.v(TAG, e.toString());
        }
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
