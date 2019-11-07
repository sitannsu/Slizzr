package com.slizzz.android.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.parse.ParseFile;
import com.parse.SaveCallback;
import com.slizzz.android.R;
import com.slizzz.android.drawer.FragmentDrawer;
import com.slizzz.android.model.UserInfo;
import com.slizzz.android.util.UiUtil;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by sjena on 12/08/18.
 */

public class ProfileActivity  extends AppCompatActivity implements View.OnClickListener{



    private FancyButton submitt;
    private EditText firstName, lastName, dob, gender, location, briefBio;
    private Toolbar mToolbar;

    private UserInfo userInfo;
    private ImageView profileImage, profile_image;
    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profile);

        inItUi();
        setToolBar();
        calendarSetUp();



    }

    private void calendarSetUp() {

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

    }
    private void updateLabel() {
        String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Log.d("sdf.format",""+sdf.format(myCalendar.getTime()));
        dob.setText(sdf.format(myCalendar.getTime()));
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

    private void inItUi() {

        submitt = (FancyButton)findViewById(R.id.submitt);
        submitt.setOnClickListener(this);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        dob = (EditText) findViewById(R.id.dob);
        gender = (EditText) findViewById(R.id.gender);
        location = (EditText) findViewById(R.id.location);
        briefBio = (EditText) findViewById(R.id.briefBio);

        userInfo = UiUtil.getLoggedInUser(this);
        if(userInfo.getFirstName() != null)
        {
            firstName.setText(userInfo.getFirstName());
        }

        if(userInfo.getLastName() != null)
        {
            lastName.setText(userInfo.getLastName());
        }


        if(userInfo.getDob() != null)
        {
            dob.setText(userInfo.getDob());
        }

        if(userInfo.getGender() != null)
        {
            gender.setText(userInfo.getGender());
        }

        if(userInfo.getLocation() != null)
        {
            location.setText(userInfo.getLocation());
        }

        if(userInfo.getGetBriefBio() != null)
        {
            briefBio.setText(userInfo.getGetBriefBio());
        }

        profileImage = (ImageView)findViewById(R.id.profileImage);
        profile_image = (ImageView)findViewById(R.id.profile_image);
        String imageUrl = UiUtil.getLoggedInUser(this).getProfileUrl();

     /*   if(imageUrl != null) {
            UiUtil.loadImageWithRemoteImageFitxy(this, profileImage, imageUrl);
        }
*/
        new ProfilePhotoAsync(imageUrl).execute();


        if(imageUrl != null) {
            UiUtil.showCircularImageUsingGlide(this, profile_image, imageUrl);
        }

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    class ProfilePhotoAsync extends AsyncTask<String, String, String> {
        public Bitmap bitmap;
        String url;

        public ProfilePhotoAsync(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(String... params) {
            // Fetching data from URI and storing in bitmap
            bitmap = DownloadImageBitmap(url);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            profileImage.setImageBitmap(bitmap);

           // saveNewUser();
        }
    }


    public static Bitmap DownloadImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("IMAGE", "Error getting bitmap", e);
        }
        return bm;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.submitt:

                UiUtil.showToast(this, "User data saved");
                Intent newIntent = new Intent(ProfileActivity.this, DashboardActivity.class);

                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                UiUtil.addFirstTimeSharedPref(this, false);
                startActivity(newIntent);

                finish();

                break;
        }

    }
}
