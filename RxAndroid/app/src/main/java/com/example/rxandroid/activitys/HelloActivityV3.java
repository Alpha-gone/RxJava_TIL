package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityHelloV3Binding;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import io.reactivex.rxjava3.core.Observable;

public class HelloActivityV3 extends RxAppCompatActivity {
    private static ActivityHelloV3Binding activity_hello_v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_hello_v3 = DataBindingUtil.setContentView(this, R.layout.activity_hello_v3);


        Observable.just("Hello, rx world!").compose(bindToLifecycle())
                .subscribe(activity_hello_v3.tvView::setText);
    }
    
}