package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityPollingBinding;
import com.example.rxandroid.logs.LogAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PollingActivity extends AppCompatActivity {
    private ActivityPollingBinding activity_polling;

    private LogAdapter logAdapter;
    private List<String> logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_polling = DataBindingUtil.setContentView(this, R.layout.activity_polling);

        setupLogger();

        activity_polling.btnPolling.setOnClickListener(view -> {
            startPolling();
        });

        activity_polling.btnPolling2.setOnClickListener(view -> {
            startPollingV2();
        });
    }

    private void startPolling(){
        Observable<String> observable = Observable.interval(0L, 3L, TimeUnit.SECONDS)
                .flatMap(o -> Observable.just("polling #1 " + o.toString()));

        observable.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::log);
    }

    private void startPollingV2(){
        Observable<String> observable2 = Observable.just("polling #2 ")
                .repeatWhen(o -> o.delay(3, TimeUnit.SECONDS));

        observable2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::log);
    }

    private void log(String log) {
        logs.add(log);
        logAdapter.clear();
        logAdapter.addAll(logs);
    }

    private void setupLogger(){
        logs = new ArrayList<>();
        logAdapter = new LogAdapter(this, new ArrayList<>());
        activity_polling.lvLog.setAdapter(logAdapter);
    }
}