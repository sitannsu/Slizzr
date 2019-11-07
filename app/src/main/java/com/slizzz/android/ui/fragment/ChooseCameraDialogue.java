package com.slizzz.android.ui.fragment;

/**
 * Created by sjena on 17/02/18.
 */

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.slizzz.android.R;
import com.slizzz.android.ui.CreateEventActivity;


/**
 * Created by sjena on 5/15/17.
 */

public class ChooseCameraDialogue extends DialogFragment implements View.OnClickListener {

    private LinearLayout upload_layout, take_photo_layout, create_new_folder;
    //private LinearLayout gallery_layout, camera_layout;
    TextView gallery, camera;

    private Context context;

    private int type;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.camera_pick_dialog, container, false);
        //gallery_layout = (LinearLayout)rootView.findViewById(R.id.gallery_layout);
        //camera_layout = (LinearLayout)rootView.findViewById(R.id.camera_layout);
        gallery = rootView.findViewById(R.id.galleryButton);
        camera = rootView.findViewById(R.id.cameraButton);



        //gallery_layout.setOnClickListener(this);
        gallery.setOnClickListener(this);
        camera.setOnClickListener(this);

        //camera_layout.setOnClickListener(this);



        return rootView;
    }






    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.galleryButton:

                ((CreateEventActivity) getActivity()).clickedLayout("gallery", type);
                getDialog().dismiss();


                break;

            case R.id.cameraButton:
                ((CreateEventActivity) getActivity()).clickedLayout("camera", type);
                getDialog().dismiss();
                break;
        }



    }
}

