package ch07.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BufferExample {
    private Observable<String> earlySource, middleSource, lateSource;

    public static void main(String[] args) {
        BufferExample bufferExample = new BufferExample();

        bufferExample.initObservable();

        CommonUtils.divSection("marbleDiagram");
        bufferExample.marbleDiagram();

        CommonUtils.divSection("bufferSkip");
        bufferExample.bufferSkip();
    }

    private void marbleDiagram(){
        CommonUtils.exampleStart();

        Observable<List<String>> observable = Observable.concat(earlySource, middleSource, lateSource)
                .buffer(3);

        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

    private void bufferSkip(){
        Observable<List<String>> observable = Observable.concat(earlySource, middleSource, lateSource)
                .buffer(2, 3);

        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

    private void initObservable(){
        String[] data = {"1", "2", "3", "4", "5", "6"};

        this.earlySource = Observable.fromArray(data)
                .take(3)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        this.middleSource = Observable.just(data[3])
                .zipWith(Observable.timer(300L, TimeUnit.MILLISECONDS), (a, b) -> a);

        this.lateSource = Observable.just(data[4], data[5])
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);
    }
}
