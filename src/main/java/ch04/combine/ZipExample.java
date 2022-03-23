package ch04.combine;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class ZipExample {
    public static void main(String[] args) {
        ZipExample zipExample = new ZipExample();

        CommonUtils.divSection("marbleDiagram");
        zipExample.marbleDiagram();

        CommonUtils.divSection("zipNumbers");
        zipExample.zipNumbers();

        CommonUtils.divSection("zipInterval");
        zipExample.zipInterval();

        CommonUtils.divSection("zipWithNumbers");
        zipExample.zipWithNumbers();
    }

    private void marbleDiagram(){
        String[] shapes = {"BALL", "PENTAGON", "STAR"};
        String[] coloredTriangles = {"2-T", "6-T", "4-T"};

        Observable<String> observable = Observable.zip(
                Observable.fromArray(shapes).map(Shape::getSuffix),
                Observable.fromArray(coloredTriangles).map(Shape::getColor),
                (suffix, color) -> color + suffix
        );

        observable.subscribe(Log::i);
    }

    private void zipNumbers(){
        Observable<Integer> observable = Observable.zip(
                Observable.just(100, 200, 300),
                Observable.just(10, 20, 30),
                Observable.just(1, 2, 3),
                (a, b, c) -> a + b + c
        );

        observable.subscribe(Log::i);
    }

    private void zipInterval(){
        Observable<String> observable = Observable.zip(
                Observable.just("RED", "GREEN", "BLUE"),
                Observable.interval(200L, TimeUnit.MILLISECONDS),
                (value, i) -> value
        );

        CommonUtils.exampleStart();
        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

    private void zipWithNumbers(){
        Observable<Integer> observable = Observable.zip(
                Observable.just(100, 200, 300),
                Observable.just(10, 20, 30),
                (a, b) -> a + b)
                .zipWith(
                        Observable.just(1, 2, 3),
                        (ab, c) -> ab + c
                );

        observable.subscribe(Log::i);
    }
}
