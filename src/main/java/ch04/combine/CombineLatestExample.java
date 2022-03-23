package ch04.combine;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class CombineLatestExample {
    public static void main(String[] args) {
        CombineLatestExample combineLatestExample = new CombineLatestExample();

        CommonUtils.divSection("marbleDiagram");
        combineLatestExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] colors = {"6", "7", "4", "2"};
        String[] shapes = {"DIAMOND", "STAR", "PENTAGON"};

        Observable<String> observable = Observable.combineLatest(
                Observable.fromArray(colors).zipWith(
                        Observable.interval(100L, TimeUnit.MILLISECONDS),
                        (color, notUsed) -> Shape.getColor(color)),
                Observable.fromArray(shapes).zipWith(
                        Observable.interval(150L, 200L, TimeUnit.MILLISECONDS),
                        (shape, notUsed) -> Shape.getSuffix(shape)),
                (color, shape) -> color + shape
        );

        observable.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
