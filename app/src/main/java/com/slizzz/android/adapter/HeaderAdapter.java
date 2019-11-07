package com.slizzz.android.adapter;

import android.content.Context;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.slizzz.android.R;

import java.util.List;

/**
 * Created by sjena on 14/08/18.
 */

public class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    String[] headerList;
    View.OnClickListener clickListner;
    int selectedPos;

    public HeaderAdapter(Context context, String[] headerList, View.OnClickListener clickListner)
    {
        this.context = context;
        this.headerList = headerList;
        this.clickListner = clickListner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
        return new HeaderHolder(v, clickListner);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if  (holder instanceof HeaderHolder) {

            final HeaderHolder itemHolder = (HeaderHolder) holder;

            if(position == selectedPos)
            {
                itemHolder.headerName.setBackground(context.getResources().getDrawable(R.drawable.rectangular_bcg));
                itemHolder.headerName.setTextColor(context.getResources().getColor(R.color.white));
            }else
            {
                itemHolder.headerName.setBackground(context.getResources().getDrawable(R.drawable.rectangular_white));
                itemHolder.headerName.setTextColor(context.getResources().getColor(R.color.black));
            }

            itemHolder.headerName.setText(headerList[position]);
            itemHolder.headerName.setTag(position);
        }

    }

    @Override
    public int getItemCount() {
        return headerList.length;
    }

    public void setSelectedPos(int selectedPos)
    {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }


    public class HeaderHolder extends RecyclerView.ViewHolder {


        private TextView headerName;





        public HeaderHolder(View itemView, View.OnClickListener listner) {
            super(itemView);

            headerName = (TextView) itemView.findViewById(R.id.headerName);



            headerName.setOnClickListener(listner);


        }
    }
}
