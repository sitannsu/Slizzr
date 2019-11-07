/*
package com.slizzz.android.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rd.PageIndicatorView;
import com.slizzz.android.R;
import com.slizzz.android.model.UserInfo;
import com.slizzz.android.ui.fragment.LoginPreviewImageFragment;
import com.slizzz.android.util.UiConstants;
import com.slizzz.android.util.UiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by sjena on 11/08/18.
 *//*


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private LoginButton txtFbLogin;
    private AccessToken mAccessToken;
    private CallbackManager callbackManager;
    private TextView  fb, sliderText;
    private ViewPager view_pager;
    LoginPreviewImageFragment  loginPreviewImageFragment1, loginPreviewImageFragment2, loginPreviewImageFragment3;
    PageIndicatorView pageIndicatorView;

    String[]sliderTextList = {"Discover people for your event","Discover events around you","Interact with vives"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        inItUI();

    }

    private void inItUI() {
        callbackManager = CallbackManager.Factory.create();
        txtFbLogin = (LoginButton) findViewById(R.id.login_button);
        fb = (TextView) findViewById(R.id.fb);
        sliderText = (TextView) findViewById(R.id.sliderText);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/AlexBrush-Regular.ttf");
        sliderText.setTypeface(face);
        fb.setOnClickListener(this);

        txtFbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getUserProfile(mAccessToken);
            }
            @Override
            public void onCancel() {

            }
            @Override
            public void onError(FacebookException error) {

            }
        });

        view_pager = (ViewPager)findViewById(R.id.view_pager);
        setupViewPager(view_pager);

        pageIndicatorView = (PageIndicatorView)findViewById(R.id.pageIndicatorView);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {*/
/*empty*//*
}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
                sliderText.setText(sliderTextList[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {*/
/*empty*//*
}
        });
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


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //You can fetch user info like this…
                        Log.d("objectobject",new Gson().toJson(object));
                            */
/*object.getJSONObject("picture").
                            getJSONObject("data").getString("url");*//*

                        //object.getString(“name”);
                        //object.getString(“email”));
                        //object.getString(“id”));

                        try {
                            storeUserData(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString( "fields", "first_name,last_name,email,birthday,gender,location,id,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
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
        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode,  data);
    }




    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id)
        {
            case R.id.fb:
                txtFbLogin.performClick();
                break;
        }

    }
}
*/
