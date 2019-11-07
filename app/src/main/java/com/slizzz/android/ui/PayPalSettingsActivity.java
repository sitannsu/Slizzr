package com.slizzz.android.ui;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daprlabs.cardstack.SwipeDeck;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

/**
 * Created by sjena on 25/10/18.
 */

public class PayPalSettingsActivity  extends AppCompatActivity {


    private Toolbar mToolbar;
    private TextView header, eventName;
    SwipeDeck cardStack;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_paypalsetting);
        event = LocalManager.getInstance().getSelectedEvent();

        setToolBar();


        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtil.showToast(PayPalSettingsActivity.this, "Account Saved");
                finish();
            }
        });


        //AddFragments.addFragmentToStack(this,null, DashboardFragment.class);


    }


    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        header = (TextView)findViewById(R.id.header);
        header.setText("PAYPAL ACCOUNT");



        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }



}
