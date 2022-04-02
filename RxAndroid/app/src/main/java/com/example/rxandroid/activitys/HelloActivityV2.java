package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityHelloV2Binding;

import io.reactivex.rxjava3.core.Observable;

public class HelloActivityV2 extends AppCompatActivity {
    private ActivityHelloV2Binding activity_hello_v2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_hello_v2 = DataBindingUtil.setContentView(this, R.layout.activity_hello_v2);

        Observable.<String>create(s -> {
            s.onNext("Hello world!");
            s.onComplete();
        }).subscribe(o -> activity_hello_v2.tvView.setText(o));

        Observable.just("Hello, world!")
                .subscribe(activity_hello_v2.tvView2::setText);
    }
}