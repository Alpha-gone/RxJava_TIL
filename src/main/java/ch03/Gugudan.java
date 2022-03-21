package ch03;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

import java.util.Scanner;

public class Gugudan {
    public static void main(String[] args) {
        Gugudan gugudan = new Gugudan();
        Scanner sc = new Scanner(System.in);
        int danInput;

        System.out.print("구구단 입력: ");
        danInput = sc.nextInt();

        CommonUtils.divSection("plainJava");
        gugudan.plainJava(danInput);

        CommonUtils.divSection("reactiveV1");
        gugudan.reactiveV1(danInput);


        CommonUtils.divSection("reactiveV2");
        gugudan.reactiveV2(danInput);

        CommonUtils.divSection("reactiveV3");
        gugudan.reactiveV3(danInput);

        CommonUtils.divSection("usingResultSelector");
        gugudan.usingResultSelector(danInput);

        sc.close();
    }

    private void plainJava(int dan){
        for (int row = 1; row < 10; row++){
            System.out.println(dan + " * " + row + " = " + dan * row);
        }
    }

    private void reactiveV1(int dan){
        Observable<Integer> observable = Observable.range(1, 9);
        observable.subscribe(row -> System.out.println(dan + " * " + row + " = " + dan * row));
    }

    private void reactiveV2(int dan){
        Function<Integer, Observable<String>> gugudan = num ->
            Observable.range(1, 9).map(row -> num + " * " + row + " = " + num * row);

        Observable<String> observable = Observable.just(dan).flatMap(gugudan);
        observable.subscribe(System.out::println);
    }

    private void reactiveV3(int dan){
        Observable<String> observable = Observable.just(dan).flatMap(num -> Observable.range(1, 9)
                                                                            .map(row -> num + " * " + row + " = " + num * row));
        observable.subscribe(System.out::println);
    }

    private void usingResultSelector(int dan){
        Observable<String> observable = Observable.just(dan)
                .flatMap(gugu -> Observable.range(1, 9),
                        (gugu, i) -> gugu + " * " + i + " = " + gugu * i);

        observable.subscribe(System.out::println);
    }
}
