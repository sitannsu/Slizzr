package com.slizzz.android.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class CHatADapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ParseUser> userList;
    View.OnClickListener onclickListner;
    public CHatADapter(Context context, List<ParseUser> userList, View.OnClickListener onclickListner)
    {
        this.context = context;
        this.userList = userList;
        this.onclickListner = onclickListner;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatHolder(v, onclickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if  (holder instanceof ChatHolder) {

            final ChatHolder itemHolder = (ChatHolder) holder;
            ParseUser user = userList.get(position);
            itemHolder.profileName.setText(user.getString("firstName")+" "+user.getString("lastName"));

            if(user.getParseFile("profilePicture") != null) {
                byte[] bitmapdata = new byte[0];
                try {
                    bitmapdata = user.getParseFile("profilePicture").getData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                itemHolder.profile_image.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length));
            }else
            {
                itemHolder.profile_image.setBackgroundResource(R.drawable.user_avatar);
            }

            itemHolder.parentLayout.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void notifyUserChanged(List<ParseUser> userList) {

        this.userList = userList;
        notifyDataSetChanged();
    }


    public class ChatHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image;
        private TextView profileName;
        private RelativeLayout parentLayout;





        public ChatHolder(View itemView, View.OnClickListener onclickListner ) {
            super(itemView);

            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            profileName = (TextView) itemView.findViewById(R.id.profileName);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parentLayout);
            parentLayout.setOnClickListener(onclickListner);









        }
    }
}
