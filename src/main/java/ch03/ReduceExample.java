package ch03;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;

public class ReduceExample {
    public static void main(String[] args) {
        ReduceExample reduceExample = new ReduceExample();
        String[] balls = {"1", "3", "5"};

        CommonUtils.divSection("marbleDiagram");
        reduceExample.marbleDiagram(balls);

        CommonUtils.divSection("biFunction");
        reduceExample.biFunction(balls);
    }

    private void marbleDiagram(String[] balls){
        Maybe<String> maybe = Observable.fromArray(balls)
                .reduce((ball1, ball2) -> ball2 + "(" + ball1 + ")");
        maybe.subscribe(System.out::println);
    }

    private void biFunction(String[] balls){
        BiFunction<String, String, String> mergeBalls = (ball1, ball2) -> ball2 + "(" + ball1 + ")";

        Maybe<String> maybe = Observable.fromArray(balls).reduce(mergeBalls);
        maybe.subscribe(System.out::println);
    }
}
