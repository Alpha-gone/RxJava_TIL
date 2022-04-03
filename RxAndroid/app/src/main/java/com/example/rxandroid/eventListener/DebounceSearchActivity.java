package com.example.rxandroid.eventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityDebounceSearchBinding;
import com.example.rxandroid.logs.LogAdapter;
import com.jakewharton.rxbinding4.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class DebounceSearchActivity extends AppCompatActivity {
    private ActivityDebounceSearchBinding activity_debounce_search;
    private Disposable mDisposable;
    private LogAdapter logAdapter;
    private List<String> logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_debounce_search = DataBindingUtil.setContentView(this, R.layout.activity_debounce_search);
        setLogs();

        mDisposable = getObservable()
                .debounce(500, TimeUnit.MICROSECONDS)
                .filter(s -> !TextUtils.isEmpty(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    private Observable<CharSequence> getObservable(){
        return Observable.create(emitter -> activity_debounce_search.edtInputSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        emitter.onNext(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        ));
    }

    private DisposableObserver<CharSequence> getObserver(){
        return new DisposableObserver<CharSequence>() {
            @Override
            public void onNext(@NonNull CharSequence charSequence) {
                log("Search " + charSequence.toString());
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
        activity_debounce_search.lvLog.setAdapter(logAdapter);
    }
}