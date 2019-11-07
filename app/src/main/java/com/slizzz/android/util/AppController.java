package com.slizzz.android.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import android.util.Base64;
import android.util.Log;


import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.parse.Parse;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.slizzz.android.model.ChatDetails;
import com.slizzz.android.model.Event;
import com.slizzz.android.model.User;

import io.fabric.sdk.android.Fabric;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sjena on 4/3/2017.
 */

public class AppController extends Application {

    private static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        printHashKey();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(ChatDetails.class);
       // Parse.initialize(this, UiConstants.AppID, UiConstants.ClientKey);



        //Parse.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        //ParseFacebookUtils.initialize(this);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //Parse.initialize(this, UiConstants.AppID, UiConstants.ClientKey);
        /*Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId(UiConstants.AppID)
                        // if defined
                        .clientKey(UiConstants.ClientKey)
                        .server("https://slizzr-server.herokuapp.com/parse/")
                        .build());*/

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(UiConstants.AppID)
                // if defined
                .clientKey(UiConstants.ClientKey)
                .server("https://slizzr-server.herokuapp.com/parse/")
                .build());



        mInstance = this;




    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }



    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.slizzz.android",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
