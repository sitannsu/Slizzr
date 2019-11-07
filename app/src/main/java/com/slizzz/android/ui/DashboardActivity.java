package com.slizzz.android.ui;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.adapter.HeaderAdapter;
import com.slizzz.android.drawer.FragmentDrawer;

import com.slizzz.android.ui.fragment.DashboardFragment;
import com.slizzz.android.util.AddFragments;
import com.slizzz.android.util.UiConstants;
import com.slizzz.android.util.UiUtil;
import com.slizzz.android.util.Utils;

import java.util.List;

/**
 * Created by sjena on 12/08/18.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, FragmentDrawer.FragmentDrawerListener{


    DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_dashboard);

        setToolBar();
        inItUi();


        AddFragments.addFragmentToStack(this,null, DashboardFragment.class);


        //getParseData();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                UiUtil.showToast(this,result.getContents());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




    private void inItUi() {


    }



    private void setToolBar() {

        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, mToolbar);
        drawerFragment.setDrawerListener(this);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.headerName:

                int pos = (Integer)v.getTag();



                break;
        }

    }


    public void addFragmentNotToStack(Fragment newFragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.activity_open_translate, R.anim.activity_close_scale, R.anim.pop_enter, R.anim.pop_out);
        ft.replace(R.id.content_frame, newFragment, newFragment.getClass().getSimpleName());
        // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //ft.addToBackStack(newFragment.getClass().getSimpleName());
        ft.commit();

    }

    public void addFragmentToStack(Fragment newFragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.pop_enter, R.anim.pop_out);
        ft.setCustomAnimations(R.anim.activity_open_translate, R.anim.activity_close_scale, R.anim.activity_open_translate, R.anim.activity_close_scale);

        ft.replace(R.id.content_frame, newFragment, newFragment.getClass().getSimpleName());
        // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(newFragment.getClass().getSimpleName());
        ft.commit();

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int index = getSupportFragmentManager().getBackStackEntryCount();
        if (index == 1) {
            finish();
        } else {
            super.onBackPressed();
        }

    }
}
