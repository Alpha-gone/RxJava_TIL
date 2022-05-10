package ch07.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class WindowExample {
    public static void main(String[] args) {
        WindowExample windowExample = new WindowExample();

        CommonUtils.divSection("marbleDiagram");
        windowExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] data = {"1", "2", "3", "4", "5", "6"};
        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> lateSource = Observable.just(data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<Observable<String>> source = Observable.concat(earlySource, middleSource, lateSource)
                .window(3);

        source.subscribe(observable -> {
            Log.dt("New Observable Started!!");
            observable.subscribe(Log::it);
        });

        CommonUtils.sleep(1000);
        CommonUtils.exampleComplete();
    }
}
