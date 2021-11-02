package com.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.quizapp.Application;
import com.quizapp.R;
import com.quizapp.common.Constants;

public class SplashActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    do {
                        sleep(Constants.SPLASH_TIMEOUT * 1);

                    }
                    while (!Application.s_Application.isFinishLoading());
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

    }


}
