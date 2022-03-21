package ch03;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.function.Predicate;

public class FilterExample {
    public static void main(String[] args) {
        FilterExample filterExample = new FilterExample();

        CommonUtils.divSection("marbleDiagram");
        filterExample.marbleDiagram();

        CommonUtils.divSection("evenNumbers");
        filterExample.evenNumbers();

        CommonUtils.divSection("otherFilter");
        filterExample.otherFilter();
    }

    private void marbleDiagram(){
        String[] objs = {"1 CIRCLE", "2 DIAMOND", "3 TRIANGLE", "4 DIAMOND", "5 CIRCLE", "6 HEXAGON"};

        //아래 람다식과 같음
        //Predicate<String> filterCode = obj -> obj.endsWith("CIRCLE");

        Observable<String> observable = Observable.fromArray(objs).filter(obj -> obj.endsWith("CIRCLE"));
        observable.subscribe(System.out::println);
    }

    private void evenNumbers(){
        Integer[] data = {100, 34, 27, 99, 50};

        Observable<Integer> observable = Observable.fromArray(data).filter(num -> num % 2 == 0);
        observable.subscribe(System.out::println);
    }

    private void otherFilter(){
        Integer[] numbers = {100, 200, 300, 400, 500};
        Single<Integer> single;
        Observable<Integer> observable;
        Observable<Integer> source = Observable.fromArray(numbers);

        single = source.first(-1);
        single.subscribe(data -> System.out.println("first() values = " + data));

        single = source.last(999);
        single.subscribe(data -> System.out.println("last() values = " + data));

        observable = source.take(3);
        observable.subscribe(data -> System.out.println("take(3) values = " + data));

        observable = source.takeLast(3);
        observable.subscribe(data -> System.out.println("takeLast(3) values = " + data));

        observable = source.skip(2);
        observable.subscribe(data -> System.out.println("skip(2) values = " + data));

        observable = source.skipLast(2);
        observable.subscribe(data -> System.out.println("skipLast(2) values = " + data));
    }
}
