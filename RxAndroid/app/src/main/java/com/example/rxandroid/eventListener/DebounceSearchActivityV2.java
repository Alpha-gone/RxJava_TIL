package com.example.rxandroid.eventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityDebounceSearchV2Binding;
import com.example.rxandroid.logs.LogAdapter;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.jakewharton.rxbinding4.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class DebounceSearchActivityV2 extends AppCompatActivity {
    private ActivityDebounceSearchV2Binding activity_debounce_search_v2;
    private Disposable mDisposable;
    private LogAdapter logAdapter;
    private List<String> logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_debounce_search_v2 = DataBindingUtil.setContentView(this, R.layout.activity_debounce_search_v2);

        setLogs();

        mDisposable = RxTextView.textChangeEvents(activity_debounce_search_v2.edtInputSearch)
                .debounce(400, TimeUnit.MICROSECONDS)
                .filter(s -> !TextUtils.isEmpty(s.getText().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserverLib());
    }

    private DisposableObserver<TextViewTextChangeEvent> getObserverLib(){
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) {
                log("Search " + textViewTextChangeEvent.getText());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void log(String log){
        logs.add(log);
        logAdapter.clear();
        logAdapter.addAll(logs);
    }

    private void setLogs(){
        logs = new ArrayList<>();
        logAdapter = new LogAdapter(this, new ArrayList<>());
        activity_debounce_search_v2.lvLog.setAdapter(logAdapter);
    }
}