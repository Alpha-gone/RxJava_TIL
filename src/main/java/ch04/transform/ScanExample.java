package ch04.transform;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

public class ScanExample {
    public static void main(String[] args) {
        ScanExample scanExample = new ScanExample();

        CommonUtils.divSection("marbleDiagram");
        scanExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] balls = {"1", "2", "3"};

        Observable<String> observable = Observable.fromArray(balls)
                .scan((ball1, ball2) -> ball2 + "(" + ball1 + ")");
        observable.subscribe(Log::i);
    }
}
