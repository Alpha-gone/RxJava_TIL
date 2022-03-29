package com.example.rxandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.rxandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

       /*activityMainBinding.btnHelloV1.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), HelloActivityV1.class);
           startActivity(intent);
       });

       activityMainBinding.btnHelloV2.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), HelloActivityV2.class);
           startActivity(intent);
       });

       activityMainBinding.btnLoop.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), LoopActivity.class);
           startActivity(intent);
       });*/
    }
}