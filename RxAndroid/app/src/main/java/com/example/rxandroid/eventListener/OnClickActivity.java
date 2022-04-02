package com.example.rxandroid.eventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityOnClickBinding;
import com.example.rxandroid.logs.LogAdapter;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class OnClickActivity extends AppCompatActivity {
    private static final int SEVEN = 7;
    private ActivityOnClickBinding activity_on_click;

    private LogAdapter logAdapter;
    private List<String> logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_on_click = DataBindingUtil.setContentView(this, R.layout.activity_on_click);

        setLogs();

        getClickEventObservable().map(s -> "clicked")
                .subscribe(getObserver());

        getClickEventObservableWithLmabda().map(s -> "clicked lambda")
                .subscribe(this::log);

        getClickEventObservableWithRxBinding().subscribe(this::log);

        getClickEventObservableExtra().map(local -> SEVEN)
                .flatMap(this::compareNumbers)
                .subscribe(this::log);

    }

    private Observable<View> getClickEventObservable(){
        return Observable.create(new ObservableOnSubscribe<View>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<View> emitter) throws Throwable {
                activity_on_click.btnClickObserver.setOnClickListener(emitter::onNext);
            }
        });
    }

    private Observable<View> getClickEventObservableWithLmabda(){
        return Observable.create(emitter -> {
            activity_on_click.btnClickObserverLambda.setOnClickListener(emitter::onNext);
        });
    }

    private Observable<String> getClickEventObservableWithRxBinding(){
        return RxView.clicks(activity_on_click.btnClickObserverBinding)
                .map(s -> "Clicked Rxbinding");
    }

    private Observable<View> getClickEventObservableExtra(){
        return Observable.create(emitter -> {
            activity_on_click.btnClickObserverExtra.setOnClickListener(emitter::onNext);
        });
    }

    private Observable<String> compareNumbers(int input){
        return Observable.just(input)
                .flatMap(in ->{
                    Random random = new Random();
                    int data = random.nextInt(10);
                    return Observable.just("local : " + in,
                            "remote : " + data,
                            "result = " + (in == data));
                });
    }

    private DisposableObserver<? super String> getObserver(){
        return new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                log(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                log(e.getMessage());
            }

            @Override
            public void onComplete() {
                log("complete");
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
        activity_on_click.lvLog.setAdapter(logAdapter);
    }
}