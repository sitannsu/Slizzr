package com.slizzz.android.ui;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.slizzz.android.R;
import com.slizzz.android.adapter.AttendeAdapter;
import com.slizzz.android.adapter.MyEventListAdapter;

/**
 * Created by sjena on 01/09/18.
 */

public class AttendingListActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView attendeRecycler;
    private Toolbar mToolbar;
    AttendeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_attendingevents);
        setToolBar();
        inItUi();




    }



    private void inItUi() {

        attendeRecycler = (RecyclerView)findViewById(R.id.attendeRecycler);



       /* attendeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new AttendeAdapter(this, null, this );
        attendeRecycler.setAdapter(adapter);*/
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


    @Override
    public void onClick(View v) {

    }
}
