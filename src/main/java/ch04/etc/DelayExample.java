package ch04.etc;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DelayExample {
    public static void main(String[] args) {
        DelayExample delayExample = new DelayExample();

        CommonUtils.divSection("marbleDiagram");
        delayExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] data = {"1", "7", "2", "3", "4"};

        CommonUtils.exampleStart();

        Observable<String> observable = Observable.fromArray(data)
                .delay(100L, TimeUnit.MILLISECONDS);
        observable.subscribe(Log::it);

        CommonUtils.sleep(1000);
    }

}
