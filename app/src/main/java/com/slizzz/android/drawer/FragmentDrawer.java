package com.slizzz.android.drawer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.slizzz.android.R;
import com.slizzz.android.ui.fragment.ChatEventTabFragment;
import com.slizzz.android.ui.fragment.ChatFragment;
import com.slizzz.android.ui.fragment.DashboardFragment;
import com.slizzz.android.ui.fragment.FindPeopleFragment;
import com.slizzz.android.ui.fragment.ManageEventsFragment;
import com.slizzz.android.ui.fragment.SettingsFragment;
import com.slizzz.android.ui.fragment.ZicketFragment;
import com.slizzz.android.util.AddFragments;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 3/16/2017.
 */

public class FragmentDrawer extends Fragment implements View.OnClickListener{
    private static String TAG = FragmentDrawer.class.getSimpleName();
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;
    private TextView profile_name,profile_email;
    private ImageView findPeople, message, settings, notification, manageEvent, zicket, home;
    //private PrefManager pref;
    View layout;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        //titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        inItUi();

        return layout;
    }

    private void inItUi() {

        findPeople = (ImageView) layout.findViewById(R.id.findPeople);
        message = (ImageView) layout.findViewById(R.id.message);
        settings = (ImageView) layout.findViewById(R.id.settings);
        notification = (ImageView) layout.findViewById(R.id.notification);
        manageEvent = (ImageView) layout.findViewById(R.id.manageEvent);
        zicket = (ImageView) layout.findViewById(R.id.zicket);
        manageEvent.setOnClickListener(this);
        settings.setOnClickListener(this);
        findPeople.setOnClickListener(this);
        zicket.setOnClickListener(this);
        message.setOnClickListener(this);
        zicket = (ImageView) layout.findViewById(R.id.zicket);
        home = (ImageView) layout.findViewById(R.id.home);
    }

    public static Intent getOpenFacebookIntent(Context context) {

        return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/1676967789086663"));
    }




    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    @Override
    public void onClick(View v) {
        mDrawerLayout.closeDrawer(containerView);
        int id = v.getId();
        switch (id)
        {
            case R.id.manageEvent:
                AddFragments.addFragmentToStack(getActivity(),null, ManageEventsFragment.class);

                break;
            case R.id.settings:
                AddFragments.addFragmentToStack(getActivity(),null, SettingsFragment.class);

                break;

            case R.id.findPeople:
                AddFragments.addFragmentToStack(getActivity(),null, FindPeopleFragment.class);

                break;

            case R.id.zicket:
                AddFragments.addFragmentToStack(getActivity(),null, ZicketFragment.class);
                break;

            case R.id.message:
                //AddFragments.addFragmentToStack(getActivity(),null, ChatFragment.class);
                AddFragments.addFragmentToStack(getActivity(),null, ChatEventTabFragment.class);
                break;

        }
    }


    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}



