package ch05;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilpExample {
    public static void main(String[] args) {
        FilpExample filpExample = new FilpExample();
        String[] objs = {"1-S", "2-T", "3-P"};

        CommonUtils.divSection("marbleDiagram");
        filpExample.marbleDiagram(objs);

        CommonUtils.divSection("observedOnRemoved");
        filpExample.observedOnRemoved(objs);
    }

    private void marbleDiagram(String[] objs){
        Observable<String> observable = Observable.fromArray(objs)
                .doOnNext(data -> Log.v("Original data = " + data))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .map(Shape::flip);

        observable.subscribe(Log::i);
        CommonUtils.sleep(500);
    }

    private void observedOnRemoved(String[] objs){
        Observable<String> observable = Observable.fromArray(objs)
                .doOnNext(data -> Log.v("Original data = " + data))
                .subscribeOn(Schedulers.newThread())
                .map(Shape::flip);

        observable.subscribe(Log::i);
        CommonUtils.sleep(500);
    }
}
