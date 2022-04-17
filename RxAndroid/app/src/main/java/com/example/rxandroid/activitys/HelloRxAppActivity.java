package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityHelloRxAppBinding;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class HelloRxAppActivity extends RxAppCompatActivity {
    private ActivityHelloRxAppBinding activityHelloRxAppBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityHelloRxAppBinding = DataBindingUtil.setContentView(this, R.layout.activity_hello_rx_app);

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                emitter.onNext("Hello world!");
                emitter.onComplete();
            }
        }).compose(bindToLifecycle())
                //.compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(e -> activityHelloRxAppBinding.tvView.setText(e.toString()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        activityHelloRxAppBinding.unbind();
    }
}