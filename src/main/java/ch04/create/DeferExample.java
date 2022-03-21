package ch04.create;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Supplier;

import java.util.Arrays;
import java.util.Iterator;

public class DeferExample {
    public static void main(String[] args) {
        Iterator<String> colors = Arrays.asList("1", "3", "5", "6").iterator();
        DeferExample deferExample = new DeferExample();

        CommonUtils.divSection("marbleDiagram");
        deferExample.marbleDiagram(colors);

        CommonUtils.divSection("notDeferred");
        deferExample.notDeferred(colors);
    }

    private void marbleDiagram(Iterator<String> colors){
        Supplier<Observable<String>> supplier = () -> getObservable(colors);
        Observable<String> observable = Observable.defer(supplier);

        observable.subscribe(val -> Log.i("Subscribe #1: " + val));
        observable.subscribe(val -> Log.i("Subscribe #2: " + val));
        CommonUtils.exampleComplete();
    }

    private void notDeferred(Iterator<String> colors){
        Observable<String> observable = getObservable(colors);

        observable.subscribe(val -> Log.i("Subscribe #1: " + val));
        observable.subscribe(val -> Log.i("Subscribe #2: " + val));
    }

    private Observable<String> getObservable(Iterator<String> colors) {
        if (colors.hasNext()) {
            String color = colors.next();
            return Observable.just(
                    Shape.getString(color, Shape.BALL),
                    Shape.getString(color, Shape.RECTANGLE),
                    Shape.getString(color, Shape.PENTAGON));
        }

        return Observable.empty();
    }
}
