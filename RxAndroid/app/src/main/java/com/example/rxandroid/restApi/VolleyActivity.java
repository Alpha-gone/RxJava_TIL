package com.example.rxandroid.restApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityVolleyBinding;
import com.example.rxandroid.logs.LogAdapter;
import com.example.rxandroid.volley.LocalVolley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VolleyActivity extends AppCompatActivity {
    private final static String URL = "http://time.jsontest.com/";

    private ActivityVolleyBinding activity_volley;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private LogAdapter logAdapter;
    private List<String> logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_volley = DataBindingUtil.setContentView(this, R.layout.activity_volley);

        setupLogger();

        LocalVolley.init(this);

        activity_volley.btnGet.setOnClickListener(view -> {
            post(getObservable());
        });

        activity_volley.btnGet2.setOnClickListener(view -> {
            post(getObservableFromCallable());
        });

        activity_volley.btnGet3.setOnClickListener(view -> {
            post(getObservableFromFuture());
        });

    }

    private void post(Observable<JSONObject> observable){
        DisposableObserver<JSONObject> observer = getObserver();

        compositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(observer)
        );
    }

    private DisposableObserver<JSONObject> getObserver() {
        return new DisposableObserver<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) {
                log(" >> " + jsonObject.toString());
            }

            @Override
            public void onError(Throwable t) {
                log(t.toString());
            }

            @Override
            public void onComplete() {
                log("complete");
            }
        };
    }


    private RequestFuture<JSONObject> getFuture(){
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest req = new JsonObjectRequest(URL, future, future);
        LocalVolley.getRequestQueue().add(req);
        return future;
    }

    private JSONObject getData() throws ExecutionException, InterruptedException {
        return getFuture().get();
    }

    private Observable<JSONObject> getObservable(){
        return Observable.defer(() -> {
           try{
               return Observable.just(getData());

           }catch (InterruptedException e){
               log("error: " + e.getMessage());
               return Observable.error(e);

           }catch (ExecutionException e){
               log("error: " + e.getCause());
               return Observable.error(e.getCause());
           }

        });
    }

    private Observable<JSONObject> getObservableFromCallable(){
        return Observable.fromCallable(this::getData);
    }

    private Observable<JSONObject> getObservableFromFuture(){
        return Observable.fromFuture(getFuture());
    }

    private void log(String log) {
        logs.add(log);
        logAdapter.clear();
        logAdapter.addAll(logs);
    }

    private void setupLogger(){
        logs = new ArrayList<>();
        logAdapter = new LogAdapter(this, new ArrayList<>());
        activity_volley.lvLog.setAdapter(logAdapter);
    }
}