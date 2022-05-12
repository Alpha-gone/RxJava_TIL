package ch08;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class BackpressureExample {
    public static void main(String[] args) {
        BackpressureExample backpressureExample = new BackpressureExample();

        CommonUtils.divSection("makeBackpressure");
        //backpressureExample.makeBackpressure();

        CommonUtils.divSection("usingBuffer");
        //backpressureExample.usingBuffer();

        CommonUtils.divSection("usingDrop");
        //backpressureExample.usingDrop();

        CommonUtils.divSection("usingLatest");
        backpressureExample.usingLatest();
    }

    private void makeBackpressure(){
        CommonUtils.exampleStart();

        PublishSubject<Integer> subject = PublishSubject.create();

        subject.observeOn(Schedulers.io())
                .subscribe(data -> {
                    CommonUtils.sleep(100);
                    Log.it(data);
                    }, err -> Log.e(err.toString()));

        for (int i = 0; i < 50_000_000; i++){
            subject.onNext(i);
        }

        subject.onComplete();
    }

    private void usingBuffer(){
        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureBuffer(128, () -> {}, BackpressureOverflowStrategy.DROP_OLDEST)
                .subscribe(data -> {
                    CommonUtils.sleep(100);
                    Log.it(data);
                }, err -> Log.e(err.toString()));
    }

    private void usingDrop(){
        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureDrop()
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100);
                    Log.it(data);
                }, err -> Log.e(err.toString()));

        CommonUtils.sleep(20_000);
    }

    private void usingLatest(){
        CommonUtils.exampleStart();

        Flowable.range(1, 50_000_000)
                .onBackpressureLatest()
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    CommonUtils.sleep(100);
                    Log.it(data);
                }, err -> Log.e(err.toString()));

        CommonUtils.sleep(20_000);
    }
}
