package com.example.rxandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.rxandroid.activitys.AsyncTaskActivity;
import com.example.rxandroid.activitys.HelloActivity;
import com.example.rxandroid.activitys.HelloActivityV2;
import com.example.rxandroid.activitys.LoopActivity;
import com.example.rxandroid.activitys.PollingActivity;
import com.example.rxandroid.activitys.TimerActivity;
import com.example.rxandroid.databinding.ActivityMainBinding;
import com.example.rxandroid.eventListener.DebounceSearchActivity;
import com.example.rxandroid.eventListener.DebounceSearchActivityV2;
import com.example.rxandroid.eventListener.OnClickActivity;
import com.example.rxandroid.eventListener.RecyclerViewActivity;
import com.example.rxandroid.restApi.VolleyActivity;

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

        activity_main.btnDebounceSearch.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DebounceSearchActivity.class);
            startActivity(intent);
        });

        activity_main.btnDebounceSearchV2.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DebounceSearchActivityV2.class);
            startActivity(intent);
        });

        activity_main.btnRecyclerView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
            startActivity(intent);
        });

        activity_main.btnAsyncTask.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AsyncTaskActivity.class);
            startActivity(intent);
        });

        activity_main.btnTimer.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
            startActivity(intent);
        });

        activity_main.btnPolling.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PollingActivity.class);
            startActivity(intent);
        });

        activity_main.btnVolley.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), VolleyActivity.class);
            startActivity(intent);
        });
    }
}