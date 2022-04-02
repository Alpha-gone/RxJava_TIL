package com.example.rxandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.rxandroid.activitys.HelloActivity;
import com.example.rxandroid.activitys.HelloActivityV2;
import com.example.rxandroid.activitys.LoopActivity;
import com.example.rxandroid.databinding.ActivityMainBinding;
import com.example.rxandroid.eventListener.OnClickActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_main = DataBindingUtil.setContentView(this, R.layout.activity_main);

       activity_main.btnHelloV1.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), HelloActivity.class);
           startActivity(intent);
       });


       activity_main.btnHelloV2.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), HelloActivityV2.class);
           startActivity(intent);
       });


       activity_main.btnLoop.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), LoopActivity.class);
           startActivity(intent);
       });

       activity_main.btnOnClick.setOnClickListener(view -> {
           Intent intent = new Intent(getApplicationContext(), OnClickActivity.class);
           startActivity(intent);
       });
    }
}