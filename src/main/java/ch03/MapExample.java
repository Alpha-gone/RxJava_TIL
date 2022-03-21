package ch03;

import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;


public class MapExample {
    public static void main(String[] args) {
        MapExample mapExample = new MapExample();

        System.out.println("-".repeat(5) + "marbleDiagram" + "-".repeat(5));
        mapExample.marbleDiagram();

        System.out.println("-".repeat(5) + "mapFunction" + "-".repeat(5));
        mapExample.mapFunction();

        System.out.println("-".repeat(5) + "mappingType" + "-".repeat(5));
        mapExample.mappingType();
    }

    private void marbleDiagram(){
        String[] balls = {"1", "2", "3", "5"};

        Observable<String> observable = Observable.fromArray(balls).map(ball -> ball + "◇");
        observable.subscribe(Log::i);
    }

    private void mapFunction(){
        Function<String, String> getDiamond = ball -> ball + "◇";
        String[] balls = {"1", "2", "3", "5"};

        Observable<String> observable = Observable.fromArray(balls).map(getDiamond);
        observable.subscribe(Log::i);
    }

    private void mappingType(){
        Function<String, Integer> ballToIndex = ball -> {
            switch (ball){
                case "RED":
                    return 1;

                case "YELLOW":
                    return 2;

                case "GREEN":
                    return 3;

                case "BLUE":
                    return 5;

                default:
                    return -1;
            }
        };

        String[] balls = {"RED", "YELLOW", "GREEN", "BLUE"};
        Observable<Integer> observable = Observable.fromArray(balls).map(ballToIndex);
        observable.subscribe(Log::i);
    }
}
