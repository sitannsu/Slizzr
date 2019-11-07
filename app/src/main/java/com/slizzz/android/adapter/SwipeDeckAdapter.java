package com.slizzz.android.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sjena on 15/08/18.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    private List<Event> data;
    private Context context;

    public SwipeDeckAdapter(List<Event> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            // normally use a viewholder
            v = inflater.inflate(R.layout.item_swipecard, parent, false);
        }
       // ((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
        Event event = data.get(position);
        ImageView eventImage = (ImageView) v.findViewById(R.id.eventImage);
        TextView eventName = (TextView)v.findViewById(R.id.eventName);
        TextView eventType = (TextView)v.findViewById(R.id.eventType);
        TextView eventTime = (TextView)v.findViewById(R.id.eventTime);
        TextView eventDesc = (TextView)v.findViewById(R.id.eventDesc);
        eventName.setText(event.getName());
        eventType.setText(event.getFeeType());
        eventTime.setText(getSTrDate(event.getUpdatedAt()));
        eventDesc.setText( event.getDescriptionText());
        byte [] bitmapdata= new byte[0];
        try {
            bitmapdata = event.getCoverImage().getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventImage.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String item = (String)getItem(position);
                //Log.i("MainActivity", item);
            }
        });

        return v;
    }

    public String getSTrDate(Date dateT)
    {
        String pattern = "HH:mm | MMMM dd yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(dateT);


        return date;
    }

    public void notifyDataSetChanged(List<Event> list) {
        this.data = list;
        notifyDataSetChanged();
    }
}