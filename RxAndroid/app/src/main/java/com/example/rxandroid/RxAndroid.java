package com.example.rxandroid;

import android.app.Application;

import com.example.rxandroid.volley.LocalVolley;

public class RxAndroid extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LocalVolley.init(getApplicationContext());
    }
}
