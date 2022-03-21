package ch04.transform;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class ConcatMapExample {
    public static void main(String[] args) {
        ConcatMapExample concatMapExample = new ConcatMapExample();

        CommonUtils.divSection("marbleDiagram");
        concatMapExample.marbleDiagram();

        CommonUtils.divSection("interLeaving");
        concatMapExample.interLeaving();
    }

    private void marbleDiagram(){
        CommonUtils.exampleStart();

        String[] balls = {"1", "3", "5"};
        Observable<String> observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                    .map(notUsed -> ball + "◇")
                    .take(2)
                );

        observable.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }

    private void interLeaving(){
        CommonUtils.exampleStart();

        String[] balls = {"1", "3", "5"};
        Observable<String> observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .flatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .map(notUsed -> ball + "◇")
                        .take(2)
                );

        observable.subscribe(Log::it);
        CommonUtils.sleep(2000);
    }
}
