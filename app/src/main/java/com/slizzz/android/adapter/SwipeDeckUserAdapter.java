package com.slizzz.android.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.UiUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sjena on 15/08/18.
 */

public class SwipeDeckUserAdapter extends BaseAdapter {

    private List<ParseUser> data;
    private Context context;

    public SwipeDeckUserAdapter(List<ParseUser> data, Context context) {
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
            v = inflater.inflate(R.layout.item_swipecard_user, parent, false);
        }
       // ((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
        ParseUser event = data.get(position);
        ImageView eventImage = (ImageView) v.findViewById(R.id.eventImage);
        TextView userName = (TextView)v.findViewById(R.id.userName);

        userName.setText(event.getString("firstName")+" "+event.getString("lastName"));

        //UiUtil.loadImageWithRemoteImageFitxy(context,eventImage, event.getUsername());

        if(event.getParseFile("profilePicture") != null) {
            byte[] bitmapdata = new byte[0];
            try {
                bitmapdata = event.getParseFile("profilePicture").getData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            eventImage.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length));
        }



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

    public void notifyDataSetChanged(List<ParseUser> list) {
        this.data = list;
        notifyDataSetChanged();
    }
}