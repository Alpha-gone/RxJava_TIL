package ch07.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class ThrottleFirstExample {
    public static void main(String[] args) {
        ThrottleFirstExample throttleFirstExample = new ThrottleFirstExample();

        CommonUtils.divSection("marbleDiagram");
        throttleFirstExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] data = {"1", "2", "3", "4", "5", "6"};
        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.just(data[0])
                .zipWith(Observable.timer(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> middleSource = Observable.just(data[1])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> lateSource = Observable.just(data[2], data[3], data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a)
                .doOnNext(Log::dt);

        Observable<String> observable = Observable.concat(earlySource, middleSource, lateSource)
                .throttleFirst(200L, TimeUnit.MILLISECONDS);

        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
}
