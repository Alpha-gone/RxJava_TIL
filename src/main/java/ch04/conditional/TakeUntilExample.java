package ch04.conditional;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class TakeUntilExample {
    public static void main(String[] args) {
        TakeUntilExample takeUntilExample = new TakeUntilExample();

        CommonUtils.divSection("marbleDiagram");
        takeUntilExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] data  = {"1", "2", "3", "4", "5", "6"};

        Observable<String> observable = Observable.fromArray(data)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (val, notUsed) -> val)
                .takeUntil(Observable.timer(500L, TimeUnit.MILLISECONDS));

        observable.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
