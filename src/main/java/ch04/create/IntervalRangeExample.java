package ch04.create;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class IntervalRangeExample {
    public static void main(String[] args) {
        IntervalRangeExample intervalRangeExample = new IntervalRangeExample();

        CommonUtils.divSection("printNumbers");
        intervalRangeExample.printNumbers();

        CommonUtils.divSection("makeWithInterval");
        intervalRangeExample.makeWithInterval();
    }

    private void printNumbers(){
        Observable<Long> observable = Observable.intervalRange(1, 5, 100L, 100L, TimeUnit.MILLISECONDS);
        observable.subscribe(Log::i);

        CommonUtils.sleep(1000);
    }

    private void makeWithInterval(){
        Observable<Long> observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(num -> num + 1)
                .take(5);
        observable.subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
