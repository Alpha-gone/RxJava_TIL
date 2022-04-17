package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityHelloCompositeBinding;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class HelloCompositeActivity extends AppCompatActivity {
    public static final String TAG = HelloCompositeActivity.class.getSimpleName();
    private ActivityHelloCompositeBinding activity_hello_composite;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_hello_composite = DataBindingUtil.setContentView(this, R.layout.activity_hello_composite);

        Disposable disposable = (Disposable) Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello world!");
                e.onComplete();
            }
        }).subscribe(activity_hello_composite.tvView::setText);

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }
}