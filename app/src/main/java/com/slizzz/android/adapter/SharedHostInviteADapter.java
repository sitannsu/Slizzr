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
import com.slizzz.android.ui.SharedHostActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjena on 08/12/18.
 */

public class SharedHostInviteADapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    List<ParseUser> attendeList;
    View.OnClickListener clickListner;
    List<Integer>selectedPositionList = new ArrayList<>();
    public SharedHostInviteADapter(Context context, List<ParseUser> attendeList, View.OnClickListener clickListner)
    {
        this.context = context;
        this.attendeList = attendeList;
        this.clickListner = clickListner;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sharehostimage, parent, false);
        return new SharedHostHolder(v, clickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if  (holder instanceof SharedHostHolder) {
            final SharedHostHolder itemHolder = (SharedHostHolder) holder;
            byte [] bitmapdata= new byte[0];
            try {
                if(attendeList.get(position).getParseFile("profilePicture") != null) {
                    bitmapdata = attendeList.get(position).getParseFile("profilePicture").getData();
                    itemHolder.profile_image.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length));
                }else
                {
                    itemHolder.profile_image.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_large));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            itemHolder.profile_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(selectedPositionList.contains(position))
                    {
                        selectedPositionList.remove((Integer)position);
                    }else
                    {
                        selectedPositionList.add(position);
                    }
                    notifyItemChanged(position);

                }
            });

            if(selectedPositionList.contains(position))
            {
                itemHolder.selectedImage.setVisibility(View.VISIBLE);
            }else
            {
                itemHolder.selectedImage.setVisibility(View.GONE);
            }


        }

    }

    public List<Integer> getSelectedPositionList()
    {
        return selectedPositionList;
    }

    @Override
    public int getItemCount() {
        return attendeList.size();
    }

    public void notifyUserChanged(List<ParseUser> attendeList) {

        this.attendeList = attendeList;
        notifyDataSetChanged();
    }


    public class SharedHostHolder extends RecyclerView.ViewHolder {


        private ImageView profile_image, selectedImage;






        public SharedHostHolder(View itemView, View.OnClickListener listner) {
            super(itemView);
            profile_image = (ImageView)itemView.findViewById(R.id.profile_image);
            selectedImage = (ImageView)itemView.findViewById(R.id.selectedImage);




        }
    }
}
