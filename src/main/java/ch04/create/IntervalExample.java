package ch04.create;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class IntervalExample {
    public static void main(String[] args) {
        IntervalExample intervalExample = new IntervalExample();

        CommonUtils.divSection("printNumbers");
        intervalExample.printNumbers();

        CommonUtils.divSection("noInitialDelay");
        intervalExample.noInitialDelay();
    }

    private void printNumbers(){
        CommonUtils.exampleStart();

        Observable<Long> observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(data -> (data + 1) * 100)
                .take(5);
        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);

    }

    private void noInitialDelay(){
        CommonUtils.exampleStart();

        Observable<Long> observable = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
                .map(data -> (data + 1) * 100)
                .take(5);
        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);

    }
}
