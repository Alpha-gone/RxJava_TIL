package ch04.transform;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class SwitchMapExample {
    public static void main(String[] args) {
        SwitchMapExample switchMapExample = new SwitchMapExample();

        CommonUtils.divSection("marbleDiagram");
        switchMapExample.marbleDiagram();

        CommonUtils.divSection("usingDoOnNext");
        switchMapExample.usingDoOnNext();
    }

    private void marbleDiagram(){
        CommonUtils.exampleStart();

        String[] balls = {"1", "3", "5"};
        Observable<String> observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "◇")
                        .take(2)
                );

        observable.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }

    private void usingDoOnNext(){
        CommonUtils.exampleStart();

        String[] balls = {"1", "3", "5"};
        Observable<String> observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .doOnNext(Log::dt)
                .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "◇")
                        .take(2)
                );

        observable.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
}
