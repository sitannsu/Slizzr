package com.slizzz.android.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.slizzz.android.R;
import com.slizzz.android.util.UiUtil;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by sjena on 25/08/18.
 */

public class MyEventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    View.OnClickListener clickListner;

    public MyEventListAdapter(Context context, View.OnClickListener clickListner)
    {
        this.context = context;
        this.clickListner = clickListner;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myevents, parent, false);
        return new MyEventHolder(v, clickListner);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if  (holder instanceof MyEventHolder) {

            final MyEventHolder itemHolder = (MyEventHolder) holder;
            itemHolder.parentLayout.setTag(position);
            itemHolder.addSharedHost.setTag(position);

            UiUtil.loadImageWithRemoteImageFitxy(context, itemHolder.profile_image, UiUtil.getLoggedInUser(context).getProfileUrl());
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class MyEventHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image;
        private RelativeLayout parentLayout;
        private FancyButton addSharedHost;





        public MyEventHolder(View itemView, View.OnClickListener listner) {
            super(itemView);

            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);
            addSharedHost = (FancyButton) itemView.findViewById(R.id.addSharedHost);






            parentLayout.setOnClickListener(listner);
            addSharedHost.setOnClickListener(listner);


        }
    }
}
