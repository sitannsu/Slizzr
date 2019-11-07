/*
package com.slizzz.android;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView fb_login;
    private AccessTokenTracker mAccessTokenTracker;
    private LoginManager mLoginManager;
    private CallbackManager mCallbackManager;
    private LoginButton fb_native_loginbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        inItUI();
 */
/*       if (mLoginManager != null) {
            mLoginManager.logOut();
        }*//*

    }

    private void inItUI() {


        fb_login = (TextView) findViewById(R.id.fb_login);
        fb_login.setOnClickListener(this);
        fb_native_loginbutton = (LoginButton) findViewById(R.id.fb_native_loginbutton);
        fb_native_loginbutton.setReadPermissions("public_profile",  "user_photos", "email", "user_birthday", "public_profile", "contact_email", "birthday");


        fbLoginCallBackRegister();
    }


    private void fbLoginCallBackRegister() {

        fb_native_loginbutton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject objectT, GraphResponse response) {
                                Log.d("response", response.toString());
                                //object = objectT;
                                handleFacebookAccessToken(loginResult.getAccessToken(), objectT);
                                //UiUtil.showProgressDialogue(MainActivity.this, "Fashinscoop","Logging in Please Wait");
                                //faceBookDataReceived(object);
                            }
                        }
                );

                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,first_name,last_name,gender,email,picture,birthday");
                request.setParameters(bundle);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        setFbStuff();

    }

    private void setFbStuff() {


        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        mLoginManager = LoginManager.getInstance();
    }



    private void handleFacebookAccessToken(final AccessToken token, JSONObject object) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);
        String emailId = null;
        if (object.has("email")) {
            try {
                Log.d("email", object.getString("email"));
                emailId = object.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //profile.setEmail(object.getString("email"));
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }


        if (emailId != null) {

        } else {

        }


    }





    @Override
    public void onClick(View v) {


        int id = v.getId();

        switch (id)
        {
            case R.id.fb_login:
                doFbLogin();
                //fb_native_loginbutton.performClick();
                break;
        }
    }

    private void doFbLogin() {
        if (AccessToken.getCurrentAccessToken() != null) {
            mLoginManager.logOut();

        } else {
            mAccessTokenTracker.startTracking();
            mLoginManager.logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday"));
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
*/
