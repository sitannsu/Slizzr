package com.slizzz.android.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.parse.LogInCallback;
import com.parse.ParseException;

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.facebook.ParseFacebookUtils;
import com.rd.PageIndicatorView;
import com.slizzz.android.R;
import com.slizzz.android.model.UserInfo;
import com.slizzz.android.ui.fragment.LoginPreviewImageFragment;
import com.slizzz.android.util.UiConstants;
import com.slizzz.android.util.UiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by sjena on 11/08/18.
 */

public class LoginActivity2 extends AppCompatActivity implements View.OnClickListener{

    private TextView  fb, sliderText;
    private ViewPager view_pager;
    LoginPreviewImageFragment  loginPreviewImageFragment1, loginPreviewImageFragment2, loginPreviewImageFragment3;
    PageIndicatorView pageIndicatorView;

    String[]sliderTextList = {"Discover people for your event","Discover events around you","Interact with vives"};
    private FrameLayout facebookFrame;
    public static final List<String> mPermissions = new ArrayList<String>() {{
        add("public_profile");
        add("email");


    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login2);
        ParseFacebookUtils.initialize(this);
        inItUI();

    }

    private void inItUI() {


        view_pager = (ViewPager)findViewById(R.id.view_pager);
        setupViewPager(view_pager);

        sliderText = (TextView) findViewById(R.id.sliderText);

        pageIndicatorView = (PageIndicatorView)findViewById(R.id.pageIndicatorView);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                sliderText.setText(sliderTextList[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/*empty*/}
        });


        facebookFrame = (FrameLayout)findViewById(R.id.facebookFrame);
        facebookFrame.setOnClickListener(this);
    }





        private void setupViewPager(ViewPager viewPager) {

            loginPreviewImageFragment1 = new LoginPreviewImageFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putInt(UiConstants.EXTRA_IMAGE, R.drawable.slide_image1);
            loginPreviewImageFragment1.setArguments(bundle1);

            loginPreviewImageFragment2 = new LoginPreviewImageFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(UiConstants.EXTRA_IMAGE, R.drawable.slide_image2);
            loginPreviewImageFragment2.setArguments(bundle2);

            loginPreviewImageFragment3 = new LoginPreviewImageFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putInt(UiConstants.EXTRA_IMAGE, R.drawable.slide_image3);
            loginPreviewImageFragment3.setArguments(bundle3);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(loginPreviewImageFragment1, "ONE");
            adapter.addFragment(loginPreviewImageFragment2, "TWO");
            adapter.addFragment(loginPreviewImageFragment3, "THREE");
            viewPager.setAdapter(adapter);

        }

        class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }



    private void storeUserData(JSONObject object) throws JSONException {
        Log.d("object",object.toString());
        UserInfo userInfo = new UserInfo();
        if(object.has("picture"))
        {

            userInfo.setProfileUrl(object.getJSONObject("picture").
                    getJSONObject("data").getString("url"));
        }

        if(object.has("first_name"))
        {

            userInfo.setFirstName(object.getString("first_name"));
        }

        if(object.has("last_name"))
        {

            userInfo.setLastName(object.getString("last_name"));
        }

        if(object.has("id"))
        {

            userInfo.setId(object.getString("id"));
        }

        if(object.has("email"))
        {

            userInfo.setEmail(object.getString("email"));
        }

        if(object.has("gender"))
        {

            userInfo.setGender(object.getString("gender"));
        }

        UiUtil.addLoginUserSharedPref(this, userInfo);
        UiUtil.addLoginToSharedPref(this,true );
        //saveUser(userInfo);
        startActivity(new Intent(LoginActivity2.this, ProfileActivity.class));

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }




    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id)
        {
            case R.id.facebookFrame:
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity2.this, mPermissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {

                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp11", "User signed up and logged in through Facebook!");
                            getUserDetailsFromFB();
                        } else {
                            Log.d("MyApp333", "User logged in through Facebook!");
                            getUserDetailsFromParse();
                            UiUtil.addLoginToSharedPref(LoginActivity2.this, true);
                            UiUtil.addFirstTimeSharedPref(LoginActivity2.this, false);
                            startActivity(new Intent(LoginActivity2.this, DashboardActivity.class));

                            finish();
                        }
                    }
                });
                break;
        }

    }


    private void getUserDetailsFromParse() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        Log.d("parseUser", " "+new Gson().toJson(parseUser));


//Fetch profile photo
        try {
            ParseFile parseFile = parseUser.getParseFile("profileThumb");
            byte[] data = parseFile.getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            //mProfileImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //mEmailID.setText(parseUser.getEmail());
        //mUsername.setText(parseUser.getUsername());

        Toast.makeText(LoginActivity2.this, "Welcome back " + parseUser.getUsername(), Toast.LENGTH_SHORT).show();

    }

    private void getUserDetailsFromFB() {


        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,picture");


        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        try {

                            Log.d("Response", response.getRawResponse());

                            String email = response.getJSONObject().getJSONObject("estimatedData").getString("email");
                            Log.d("email",""+email);
                            Log.d("email",""+email);


                            String name = response.getJSONObject().getString("name");
                            //mUsername.setText(name);

                            JSONObject picture = response.getJSONObject().getJSONObject("picture");
                            JSONObject data = picture.getJSONObject("data");

                            //  Returns a 50x50 profile picture
                            String pictureUrl = data.getString("url");

                            Log.d("Profile pic", "url: " + pictureUrl);

                            //new ProfileActivity.ProfilePhotoAsync(pictureUrl).execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
