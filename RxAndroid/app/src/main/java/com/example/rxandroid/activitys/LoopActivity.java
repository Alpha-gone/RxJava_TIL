package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityLoopBinding;
import com.example.rxandroid.logs.LogAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class LoopActivity extends AppCompatActivity {
    private static final String TAG = LoopActivity.class.getSimpleName();

    private ActivityLoopBinding activity_loop;

    private LogAdapter logAdapter;
    private List<String> logs;

    Iterable<String> samples = Arrays.asList("banana", "orange", "apple", "apple mango", "melon", "watermelon");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_loop = DataBindingUtil.setContentView(this, R.layout.activity_loop);

        setLogs();

        activity_loop.btnLoop.setOnClickListener(view ->{
            log(">>>>> get an apple :: java");
            for(String str: samples){
                if(str.contains("apple")){
                    log(str);
                    return;
                }
            }
        });

        activity_loop.btnLoop2.setOnClickListener(view -> {
            log(">>>>> get an apple :: rx 2.x");
            Observable.fromIterable(samples)
                    .filter(s -> s.contains("apple"))
                    .first("Not Found")
                    .subscribe(this::log);
        });
    }

    private void log(String log){
        logs.add(log);
        logAdapter.clear();
        logAdapter.addAll(logs);
    }

    private void setLogs(){
        logs = new ArrayList<>();
        logAdapter = new LogAdapter(this, new ArrayList<>());
        activity_loop.lvLog.setAdapter(logAdapter);
    }
}