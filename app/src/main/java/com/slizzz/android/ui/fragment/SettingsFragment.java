package com.slizzz.android.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.slizzz.android.R;
import com.slizzz.android.ui.HelpActivity;
import com.slizzz.android.ui.PayPalSettingsActivity;
import com.slizzz.android.ui.PrivacyPolicyActivity;
import com.slizzz.android.ui.SplashActivity;
import com.slizzz.android.util.UiUtil;

/**
 * Created by sjena on 26/08/18.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener{


    private View view;
    private SeekBar seekBar;
    private TextView selectedDistance;
    private RelativeLayout privacyPolicyLayout, logoutRL, helpRL, paypalRL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        inItUi();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Toast.makeText(getActivity(),""+
                         i, Toast.LENGTH_SHORT).show();
                setDistance(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return view;
    }

    private void setDistance(int i) {
        if(i < 1000)
        {
            selectedDistance.setText(i+"M");
        }else
        {
            float distance = (float)i/1000;
            selectedDistance.setText(distance+"KM");
        }
    }

    private void inItUi() {

        selectedDistance = (TextView)view.findViewById(R.id.selectedDistance);
        privacyPolicyLayout = (RelativeLayout) view.findViewById(R.id.privacyPolicyLayout);
        privacyPolicyLayout.setOnClickListener(this);

        logoutRL = (RelativeLayout) view.findViewById(R.id.logoutRL);
        logoutRL.setOnClickListener(this);

        helpRL = (RelativeLayout) view.findViewById(R.id.helpRL);
        helpRL.setOnClickListener(this);

        paypalRL = (RelativeLayout) view.findViewById(R.id.paypalRL);
        paypalRL.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id)
        {
            case R.id.privacyPolicyLayout:

                startActivity(new Intent(getActivity(), PrivacyPolicyActivity.class));

                break;

            case R.id.logoutRL:
                logoutDialog();
                break;

            case R.id.helpRL:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;

            case R.id.paypalRL:
                startActivity(new Intent(getActivity(), PayPalSettingsActivity.class));
                break;

        }

    }

    public void logoutDialog(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_dialog_new);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView done = (TextView) dialog.findViewById(R.id.done);
        TextView discard = (TextView) dialog.findViewById(R.id.discard);
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UiUtil.addLoginToSharedPref(getActivity(), false);
                Intent newIntent = new Intent(getActivity(), SplashActivity.class);

                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().finish();
                UiUtil.addLoginToSharedPref(getActivity(), false);
                startActivity(newIntent);
            }
        });
        dialog.show();

    }
}
