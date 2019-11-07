package com.slizzz.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import com.slizzz.android.R;
import com.slizzz.android.util.UiUtil;

/**
 * Created by sjena on 12/08/18.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(UiUtil.isLoogedIn(SplashActivity.this))
                {
                    if(UiUtil.isFirsttime(SplashActivity.this))
                    {
                        startActivity(new Intent(SplashActivity.this, ProfileActivity.class));
                    }else
                    {
                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    }

                }else {

                    startActivity(new Intent(SplashActivity.this, LoginActivity2.class));
                }
                finish();

            }
        }, 2000);

    }


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
    }

}
