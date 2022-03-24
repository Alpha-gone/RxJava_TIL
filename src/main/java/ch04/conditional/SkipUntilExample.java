package ch04.conditional;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SkipUntilExample {
    public static void main(String[] args) {
        SkipUntilExample skipUntilExample = new SkipUntilExample();

        CommonUtils.divSection("marbleDiagram");
        skipUntilExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] data  = {"1", "2", "3", "4", "5", "6"};

        Observable<String> observable = Observable.fromArray(data)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (val, notUsed) -> val)
                .skipUntil(Observable.timer(450L, TimeUnit.MILLISECONDS));

        observable.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
