package com.example.rxandroid.eventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rxandroid.R;
import com.example.rxandroid.databinding.ActivityRecyclerViewBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecyclerViewActivity extends AppCompatActivity {
    private ActivityRecyclerViewBinding activity_recycler_view;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity_recycler_view = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        activity_recycler_view.rvRView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter();

        activity_recycler_view.rvRView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.getItemPublishSubject()
                .subscribe(s -> tosast(s.getTitle()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(recyclerViewAdapter == null){
            return;
        }

        getItemObervable().observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    recyclerViewAdapter.updateItems(item);
                    recyclerViewAdapter.notifyDataSetChanged();
                });
    }

    private Observable<RecyclerItem> getItemObervable(){
        final PackageManager pm = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        return Observable.fromIterable(pm.queryIntentActivities(intent, 0))
                .sorted(new ResolveInfo.DisplayNameComparator(pm))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(item ->{
                    Drawable image = item.activityInfo.loadIcon(pm);
                    String title = item.activityInfo.loadLabel(pm).toString();
                    return RecyclerItem.of(image, title);
                });
    }

    private void tosast(String title){
        Toast.makeText(this, title, Toast.LENGTH_LONG).show();
    }
}