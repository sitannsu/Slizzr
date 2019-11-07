package com.slizzz.android.ui.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.slizzz.android.R;
import com.slizzz.android.util.UiConstants;

/**
 * Created by sjena on 13/08/18.
 */

public class LoginPreviewImageFragment extends Fragment {

    ImageView previewImage;

    private View view;
    int drawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_previewlogin, container, false);

        drawable = getArguments().getInt(UiConstants.EXTRA_IMAGE);
        previewImage = (ImageView)view.findViewById(R.id.previewImage);
        previewImage.setImageDrawable(getResources().getDrawable(drawable));

        return view;
    }
}
