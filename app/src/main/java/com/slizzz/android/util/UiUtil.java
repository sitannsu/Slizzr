package com.slizzz.android.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.slizzz.android.model.UserInfo;

/**
 * Created by sjena on 12/08/18.
 */

public class UiUtil {


    public static ProgressDialog pDialogue;
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private static int PRIVATE_MODE = 0;




    public static void showToast(Context context, String msg) {
        if(context == null)
            return;
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }


    public static void showProgressDialogue(Context context, String tittle, String msg) {
        if(context != null) {
            if (pDialogue != null && pDialogue.isShowing()) {
                pDialogue.setTitle(tittle);
                pDialogue.setMessage(msg);
            } else {
                pDialogue = new ProgressDialog(context);
                pDialogue.setTitle(tittle);
                pDialogue.setMessage(msg);
                pDialogue.show();
            }
            pDialogue.setCanceledOnTouchOutside(true);
            pDialogue.setCancelable(true);
        }
    }

    public static void cancelProgressDialogue() {
        try {
            if (pDialogue != null && pDialogue.isShowing()) {
                pDialogue.dismiss();
                pDialogue = null;
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        }
    }


    public static void addLoginUserSharedPref(Context context, UserInfo user) {
        // UserProfile profile = new UserProfile();
        //profile.setName(input_name.getText().toString());
        pref = context.getSharedPreferences(UiConstants.PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();
        editor.putString(UiConstants.LOGGEDIN_USER, new Gson().toJson(user));
        editor.commit();
    }


    public static UserInfo getLoggedInUser(Context context) {
        pref = context.getSharedPreferences(UiConstants.PREF_NAME, PRIVATE_MODE);
        UserInfo loggedInUser = null;
        if(pref.getString(UiConstants.LOGGEDIN_USER, null) != null) {
            loggedInUser = new Gson().fromJson(pref.getString(UiConstants.LOGGEDIN_USER, null), UserInfo.class);
            return loggedInUser;
        }
        return loggedInUser;
    }


    public static void addLoginToSharedPref(Context context, Boolean isLoogedIn) {
        // UserProfile profile = new UserProfile();
        //profile.setName(input_name.getText().toString());
        pref = context.getSharedPreferences(UiConstants.PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();
        editor.putBoolean(UiConstants.IS_LOGGEDIN, isLoogedIn);
        editor.commit();
    }

    public static Boolean isLoogedIn(Context context) {
        pref = context.getSharedPreferences(UiConstants.PREF_NAME, PRIVATE_MODE);
        Boolean is_loggedin = pref.getBoolean(UiConstants.IS_LOGGEDIN, false);
        return is_loggedin;
    }


    public static void addFirstTimeSharedPref(Context context, Boolean isLoogedIn) {
        // UserProfile profile = new UserProfile();
        //profile.setName(input_name.getText().toString());
        pref = context.getSharedPreferences(UiConstants.PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();
        editor.putBoolean(UiConstants.IS_PROFILESET, isLoogedIn);
        editor.commit();
    }

    public static Boolean isFirsttime(Context context) {
        pref = context.getSharedPreferences(UiConstants.PREF_NAME, PRIVATE_MODE);
        Boolean is_loggedin = pref.getBoolean(UiConstants.IS_PROFILESET, true);
        return is_loggedin;
    }


    public static void loadImageWithRemoteImageFitxy(Context context, ImageView view, String url) {
        if(context != null) {
            Glide.with(context)
                    .load(url)


                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        }
    }

    public static void showCircularImageUsingGlide(final Context context, final ImageView imageView, final String imageUri) {

        if(context != null)
            Glide.with(context).load(imageUri).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    imageView.setImageDrawable(circularBitmapDrawable);
                }
            });
    }
}
