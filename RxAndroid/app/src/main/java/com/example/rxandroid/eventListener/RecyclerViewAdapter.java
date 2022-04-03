package com.example.rxandroid.eventListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxandroid.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<RecyclerItem> itemList = new ArrayList<>();

    private PublishSubject<RecyclerItem> publishSubject;

    RecyclerViewAdapter(){
        this.publishSubject = PublishSubject.create();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final RecyclerItem item = itemList.get(position);

        holder.ivItem.setImageDrawable(item.getImage());
        holder.tvItem.setText(item.getTitle());

        holder.getClickObserver(item).subscribe(publishSubject);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateItems(List<RecyclerItem> items) {
        itemList.addAll(items);
    }

    public void updateItems(RecyclerItem item) {
        itemList.add(item);
    }

    public PublishSubject<RecyclerItem> getItemPublishSubject(){
        return publishSubject;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivItem;
        TextView tvItem;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItem = itemView.findViewById(R.id.ivItem);
            tvItem = itemView.findViewById(R.id.tvItem);
        }

        Observable<RecyclerItem> getClickObserver(RecyclerItem item){
            return Observable.create(emitter -> {
                itemView.setOnClickListener(v -> emitter.onNext(item));
            });
        }


    }
}
