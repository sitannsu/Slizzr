package com.slizzz.android.ui;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slizzz.android.R;
import com.slizzz.android.adapter.HelpAdapter;
import com.stone.vega.library.VegaLayoutManager;

/**
 * Created by sjena on 08/09/18.
 */

public class HelpActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    RecyclerView helpRecycler;
    private HelpAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_help);


        setToolBar();
        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Help");

        inItUi();



    }

    private void inItUi() {

        helpRecycler = (RecyclerView)findViewById(R.id.helpRecycler);
        adapter = new HelpAdapter(this);
        helpRecycler.setLayoutManager(new LinearLayoutManager(this));
        helpRecycler.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }



    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }
}
