package com.slizzz.android.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.slizzz.android.R;

/**
 * Created by sjena on 08/09/18.
 */

public class HelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public HelpAdapter(Context context)
    {

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_help, parent, false);
        return new HelpHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if  (holder instanceof HelpHolder) {

            final HelpHolder itemHolder = (HelpHolder) holder;
            itemHolder.helpName.setText("Help"+position);



        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    public class HelpHolder extends RecyclerView.ViewHolder {


        private TextView helpName;





        public HelpHolder(View itemView ) {
            super(itemView);

            helpName = (TextView) itemView.findViewById(R.id.helpName);



            //headerName.setOnClickListener(listner);


        }
    }
}
