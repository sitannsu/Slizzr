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
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.slizzz.android.R;
import com.slizzz.android.model.ChatDetails;
import com.slizzz.android.util.LocalManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sjena on 26/10/18.
 */

public class ChatDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    List<ParseObject> chatList;
    ParseUser withChatUser, user;

    public ChatDetailsAdapter(Context context, List<ParseObject>chatList)
    {
        this.context = context;
        this.chatList = chatList;

        withChatUser = LocalManager.getInstance().getParseUser();
        user = ParseUser.getCurrentUser();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        View view;
        switch (viewType) {
            case 0:

                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mychat, parent, false);
                return new CHatMeHolder(v);



            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hischat, parent, false);
                return new CHatHeHolder(v);




        }
        return null;

    }

    public String getSTrDate(Date dateT)
    {
        String pattern = "HH:mm | MMMM dd yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(dateT);


        return date;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ParseObject parseObject = chatList.get(position);
        if(holder instanceof CHatMeHolder)
        {

            final CHatMeHolder cHatMeHolder = (CHatMeHolder) holder;
            if(user.getParseFile("profilePicture") != null) {
                byte[] bitmapdata = new byte[0];
                try {
                    bitmapdata = user.getParseFile("profilePicture").getData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cHatMeHolder.profile_image.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length));

            }else
            {
                cHatMeHolder.profile_image.setBackgroundResource(R.drawable.user_avatar);
            }
            cHatMeHolder.chatText.setText(parseObject.getString("lastMessage"));
            cHatMeHolder.chatTime.setText(getSTrDate(parseObject.getDate("timeStamp")));

        }else
        {
            final CHatHeHolder chatHeHolder = (CHatHeHolder) holder;
            if(withChatUser.getParseFile("profilePicture") != null) {
                byte[] bitmapdata = new byte[0];
                try {
                    bitmapdata = withChatUser.getParseFile("profilePicture").getData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                chatHeHolder.profile_image.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length));

            }else
            {
                chatHeHolder.profile_image.setBackgroundResource(R.drawable.user_avatar);
            }
            chatHeHolder.chatText.setText(parseObject.getString("lastMessage"));
            chatHeHolder.chatTime.setText(getSTrDate(parseObject.getDate("timeStamp")));
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    @Override
    public int getItemViewType(int position) {

        if(chatList.get(position).get("sender").equals(user.getObjectId()))
        {
            return 0;
        }else
        {
            return 1;
        }
    }

    public void notifyDayta(List<ParseObject> chatList) {

        this.chatList = chatList;
        notifyDataSetChanged();
    }


    public class CHatHeHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image;

        private TextView chatText, chatTime ;





        public CHatHeHolder(View itemView ) {
            super(itemView);
            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            chatText = (TextView) itemView.findViewById(R.id.chatText);
            chatTime = (TextView) itemView.findViewById(R.id.chatTime);





        }
    }

    public class CHatMeHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image;

        private TextView chatText, chatTime ;





        public CHatMeHolder(View itemView ) {
            super(itemView);
            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            chatText = (TextView) itemView.findViewById(R.id.chatText);
            chatTime = (TextView) itemView.findViewById(R.id.chatTime);





        }
    }

}
