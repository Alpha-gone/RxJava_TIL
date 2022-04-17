package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityHelloBinding;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class HelloActivity extends AppCompatActivity {
    private ActivityHelloBinding activity_hello;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_hello = DataBindingUtil.setContentView(this, R.layout.activity_hello);

        Observer<String> observer = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                activity_hello.tvView.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                activity_hello.tvView.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("Hello world!");
                emitter.onComplete();
            }
        }).subscribe(observer);

        disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("Hello world!");
                emitter.onComplete();
            }
        }).subscribeWith(disposableObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(!disposable.isDisposed()){
            disposable.dispose();
        }
    }
}