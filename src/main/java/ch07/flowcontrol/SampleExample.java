package ch07.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SampleExample {
    private Observable<String> earlySource, lateSource;

    public static void main(String[] args) {
        SampleExample sampleExample = new SampleExample();

        sampleExample.initObservable();

        CommonUtils.divSection("marbleDiagram");
        sampleExample.marbleDiagram();

        CommonUtils.divSection("emitLast");
        sampleExample.emitLast();
    }

    private void marbleDiagram(){
        CommonUtils.exampleStart();

        Observable<String> source = Observable.concat(earlySource, lateSource)
                .sample(300L, TimeUnit.MILLISECONDS);

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

    private void emitLast(){
        CommonUtils.exampleStart();

        Observable<String> source = Observable.concat(earlySource, lateSource)
                .sample(300L, TimeUnit.MILLISECONDS, true);

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

    private void initObservable(){
        String[] data = {"1", "7", "2", "3", "6"};

        this.earlySource = Observable.fromArray(data)
                .take(4)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        this.lateSource = Observable.just(data[4])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);
    }
}
