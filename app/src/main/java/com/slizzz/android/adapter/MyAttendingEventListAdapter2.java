package com.slizzz.android.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by sjena on 25/08/18.
 */

public class MyAttendingEventListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    View.OnClickListener clickListner;

    List<Event> eventList;
    public MyAttendingEventListAdapter2(Context context, View.OnClickListener clickListner, List<Event> eventList)
    {
        this.context = context;
        this.clickListner = clickListner;
        this.eventList = eventList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myevents2, parent, false);
        return new MyEventHolder(v, clickListner);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if  (holder instanceof MyEventHolder) {

            final MyEventHolder itemHolder = (MyEventHolder) holder;
            itemHolder.parentLayout.setTag(position);
            Event event = eventList.get(position);
            byte [] bitmapdata= new byte[0];
            try {
                bitmapdata = event.getCoverImage().getData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            itemHolder.eventName.setText(event.getName());
            itemHolder.dateTime.setText(event.getDate().toString());
            itemHolder.attendeCount.setText(event.getAttendeeCount()+" Attendee");
            itemHolder.profile_image.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length));

            //UiUtil.loadImageWithRemoteImageFitxy(context, itemHolder.profile_image, UiUtil.getLoggedInUser(context).getProfileUrl());
        }
    }

    public void notifyEvetChanged(List<Event> eventList)
    {

        this.eventList = eventList;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public class MyEventHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image;
        private RelativeLayout parentLayout;
        private TextView eventName, eventLocation, dateTime;
        private FancyButton attendeCount;





        public MyEventHolder(View itemView, View.OnClickListener listner) {
            super(itemView);

            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            eventName = (TextView) itemView.findViewById(R.id.eventName);
            dateTime = (TextView) itemView.findViewById(R.id.dateTime);
            attendeCount = (FancyButton) itemView.findViewById(R.id.attendeCount);
            eventLocation = (TextView) itemView.findViewById(R.id.eventLocation);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);






            parentLayout.setOnClickListener(listner);


        }
    }
}
