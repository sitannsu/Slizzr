package com.slizzz.android.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.util.UiUtil;

import java.util.List;

/**
 * Created by sjena on 26/10/18.
 */

public class MyEventAttenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ParseUser> userList;
    public MyEventAttenderAdapter(Context context, List<ParseUser> userList)
    {
        this.context = context;
        this.userList = userList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myevents, parent, false);
        return new MyEventHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if  (holder instanceof MyEventHolder) {

            final MyEventHolder itemHolder = (MyEventHolder) holder;
            ParseUser user = userList.get(position);
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
    public int getItemCount() {
        return userList.size();
    }



    public class MyEventHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image;
        private TextView profileName;





        public MyEventHolder(View itemView ) {
            super(itemView);

            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profileName);









        }
    }
}
