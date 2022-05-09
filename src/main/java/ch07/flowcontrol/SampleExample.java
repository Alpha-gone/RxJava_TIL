package ch07.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SampleExample {
    public static void main(String[] args) {
        SampleExample sampleExample = new SampleExample();
        String[] data = {"1", "7", "2", "3", "6"};

        CommonUtils.divSection("marbleDiagram");
        sampleExample.marbleDiagram(data);

        CommonUtils.divSection("emitLast");
        sampleExample.emitLast(data);
    }

    private void marbleDiagram(String[] data){
        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.fromArray(data)
                .take(4)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> lateSource = Observable.just(data[4])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> source = Observable.concat(earlySource, lateSource)
                .sample(300L, TimeUnit.MILLISECONDS);

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

    private void emitLast(String[] data){
        CommonUtils.exampleStart();

        Observable<String> earlySource = Observable.fromArray(data)
                .take(4)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> lateSource = Observable.just(data[4])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        Observable<String> source = Observable.concat(earlySource, lateSource)
                .sample(300L, TimeUnit.MILLISECONDS, true);

        source.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }
}
