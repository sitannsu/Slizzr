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
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;
import com.slizzz.android.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sjena on 02/09/18.
 */

public class AttendeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    List<ParseUser>attendeList;
    View.OnClickListener clickListner;
    Event event;

    public AttendeAdapter(Context context, List<ParseUser>attendeList, View.OnClickListener clickListner, Event event)
    {
        this.context = context;
        this.attendeList = attendeList;
        this.clickListner = clickListner;
        this.event = event;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        View view;
        switch (viewType) {
            case 0:

                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myfirstevents, parent, false);
                return new FirstHolder(v, clickListner);



            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myeventattender, parent, false);
                return new AttendeHolder(v);




        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == 0)
        {

            final FirstHolder fitemHolder = (FirstHolder) holder;

            byte [] bitmapdata= new byte[0];
            try {
                bitmapdata = event.getCoverImage().getData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            fitemHolder.eventImage.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length));
            fitemHolder.eventName.setText(event.getName());

            Calendar c = Calendar.getInstance();
            c.setTime(event.getDate());
            fitemHolder.eventDay.setText(""+c.get(Calendar.DAY_OF_MONTH));
            SimpleDateFormat format1=new SimpleDateFormat("MMM yyyy");


            fitemHolder.eventMonth.setText(format1.format(event.getDate()));

        }else
        {
            final AttendeHolder itemHolder = (AttendeHolder) holder;
            ParseUser user = attendeList.get(position-1);
            itemHolder.profileName.setText(user.getString("firstName")+" "+user.getString("lastName"));

            byte [] bitmapdata= new byte[0];
            try {
                bitmapdata = user.getParseFile("profilePicture").getData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            itemHolder.profile_image.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length));
        }

    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0)
        {
            return 0;
        }else
        {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return attendeList.size()+1;
    }

    public void notifyUserChanged(List<ParseUser> attendeList) {

        this.attendeList = attendeList;
        notifyDataSetChanged();
    }

    public class AttendeHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image;
        private TextView profileName;





        public AttendeHolder(View itemView ) {
            super(itemView);

            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profileName);









        }
    }


    public class FirstHolder extends RecyclerView.ViewHolder {


        private ImageView eventImage;
        private RelativeLayout parentLayout;
        private TextView eventName, eventDay, eventMonth;





        public FirstHolder(View itemView, View.OnClickListener listner) {
            super(itemView);
            eventImage = (ImageView)itemView.findViewById(R.id.eventImage);
            eventName = (TextView) itemView.findViewById(R.id.eventName);
            eventDay = (TextView) itemView.findViewById(R.id.eventDay);
            eventMonth = (TextView) itemView.findViewById(R.id.eventMonth);




        }
    }
}
