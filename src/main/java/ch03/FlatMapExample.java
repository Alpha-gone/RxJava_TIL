package ch03;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

public class FlatMapExample {
    public static void main(String[] args) {
        FlatMapExample flatMapExample = new FlatMapExample();

        CommonUtils.divSection("marbleDiagram");
        flatMapExample.marbleDiagram();

        CommonUtils.divSection("flatMaplambda");
        flatMapExample.flatMaplambda();
    }

    private void marbleDiagram(){
        Function<String, Observable<String>> getDoubleDiamonds = ball -> Observable.just(ball + "◇", ball + "◇");

        String[] balls = {"1", "3", "5"};

        Observable<String> observable = Observable.fromArray(balls).flatMap(getDoubleDiamonds);

        observable.subscribe(Log::i);
    }

    private void flatMaplambda(){
        String[] balls = {"1", "3", "5"};

        Observable<String> observable = Observable.fromArray(balls).flatMap(ball -> Observable.just(ball + "◇", ball + "◇"));

        observable.subscribe(Log::i);
    }
}
