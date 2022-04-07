package com.example.rxandroid.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityAsyncTaskBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class AsyncTaskActivity extends AppCompatActivity {
    private final static String TAG = AsyncTaskActivity.class.getSimpleName();
    private ActivityAsyncTaskBinding activity_async_task;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_async_task = DataBindingUtil.setContentView(this, R.layout.activity_async_task);

        //initAndroidAsync();
        initRxAsync();

    }

    private void initAndroidAsync(){
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("Hello", "async", "world");
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder word = new StringBuilder();

            for(String s : strings){
                word.append(s).append(" ");
            }
            return word.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            activity_async_task.tvView.setText(s);
        }
    }

    private void initRxAsync(){
        Observable.just("Hello", "rx", "world")
                .reduce((x, y) -> x + " " + y)
                .observeOn(AndroidSchedulers.mainThread())
                //.subscribe(getObserver())
                .subscribe(
                        activity_async_task.tvView::setText,
                        e -> Log.e(TAG, e.getMessage()),
                        () -> Log.i(TAG, "done")
                );
    }

    private MaybeObserver<String> getObserver(){
        return new MaybeObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                activity_async_task.tvView.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "done");
            }
        };
    }
}