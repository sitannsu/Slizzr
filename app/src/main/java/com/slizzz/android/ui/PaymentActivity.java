package com.slizzz.android.ui;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daprlabs.cardstack.SwipeDeck;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.LocalManager;

/**
 * Created by sjena on 25/10/18.
 */

public class PaymentActivity extends AppCompatActivity {



    private Toolbar mToolbar;
    private TextView header, eventName;
    SwipeDeck cardStack;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_payment);


        setToolBar();
        inItUi();


        //AddFragments.addFragmentToStack(this,null, DashboardFragment.class);


    }

    private void setToolBar() {
    }

    private void inItUi() {
    }


}
