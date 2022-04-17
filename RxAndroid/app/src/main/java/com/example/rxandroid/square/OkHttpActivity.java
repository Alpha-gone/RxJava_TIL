package com.example.rxandroid.square;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityOkHttpBinding;
import com.example.rxandroid.logs.LogAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OkHttpActivity extends AppCompatActivity {
    private ActivityOkHttpBinding activity_ok_http;
    private static final String NAME = "jungjoonpark-pandora";
    private static final String REPO = "rxAndroid";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_ok_http = DataBindingUtil.setContentView(this, R.layout.activity_ok_http);

        setupLogger();

        activity_ok_http.btnRetrofit.setOnClickListener(view -> {
            startRetrofit();
        });

        activity_ok_http.btnRetrofitOkHttp.setOnClickListener(view -> {
            startOkHttp();
        });

        activity_ok_http.btnRetrofitOkHttpRx.setOnClickListener(view -> {
            startRx();
        });

    }

    //retrofit + okHttp(call의 내부)
    private void startRetrofit(){
        GitHubServiceApi serviceApi = RestfulAdapter.getInstance().getSimpleApi();
        Call<List<Contributor>> call = serviceApi.getCallContributors(NAME, REPO);

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                if(response.isSuccessful()){
                    List<Contributor> contributors = response.body();

                    for(Contributor contributor: contributors){
                        log(contributor.toString());
                    }
                }else{
                    log("not successful");
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                log(t.getMessage());
            }
        });
    }

    //retrofit + okHttp
    private void startOkHttp(){
        GitHubServiceApi serviceApi = RestfulAdapter.getInstance().getServiceApi();
        Call<List<Contributor>> call = serviceApi.getCallContributors(NAME, REPO);

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                if(response.isSuccessful()){
                    List<Contributor> contributors = response.body();

                    for(Contributor contributor: contributors){
                        log(contributor.toString());
                    }
                }else{
                    log("not successful");
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                log(t.getMessage());
            }
        });
    }

    private void startRx(){
        GitHubServiceApi serviceApi = RestfulAdapter.getInstance().getServiceApi();
        Observable<List<Contributor>> observable = serviceApi.getObContributors(NAME, REPO);

        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableObserver<List<Contributor>>() {
                                    @Override
                                    public void onNext(@NonNull List<Contributor> contributors) {
                                        for (Contributor contributor : contributors){
                                            log(contributor.toString());
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        log(e.getMessage());
                                    }

                                    @Override
                                    public void onComplete() {
                                        log("complete");
                                    }
                                }));

    }

    private LogAdapter adapter;
    private List<String> logs;

    private void log(String log){
        logs.add(log);
        adapter.clear();
        adapter.addAll(logs);
    }

    private void setupLogger(){
        logs = new ArrayList<>();
        adapter = new LogAdapter(this, new ArrayList<>());
        activity_ok_http.lvLog.setAdapter(adapter);
    }
}