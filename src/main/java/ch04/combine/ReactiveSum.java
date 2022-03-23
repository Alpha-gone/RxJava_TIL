package ch04.combine;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.Scanner;

public class ReactiveSum {
    public static void main(String[] args) {
        ReactiveSum reactiveSum = new ReactiveSum();

        CommonUtils.divSection("reactiveSum");
        reactiveSum.runSum();
    }

    private void runSum(){
        ConnectableObservable<String> connectableObservable = userInput();
        Observable<Integer> inputA = connectableObservable
                .filter(str -> str.startsWith("a:"))
                .map(str -> str.replace("a:", ""))
                .map(Integer::parseInt);
        Observable<Integer> inputB = connectableObservable
                .filter(str -> str.startsWith("b:"))
                .map(str -> str.replace("b:", ""))
                .map(Integer::parseInt);

        Observable.combineLatest(
                inputA.startWith(Observable.just(0)),
                inputB.startWith(Observable.just(0)),
                (x, y) -> x + y)
                .subscribe(res -> System.out.println("Result: " + res));

        connectableObservable.connect();
    }

    private ConnectableObservable<String> userInput(){
        return Observable.create(
                (ObservableEmitter<String> emiiter) ->{
                    Scanner sc = new Scanner(System.in);
                    while (true){
                        System.out.print("Input: ");
                        String input = sc.nextLine();
                        emiiter.onNext(input);

                        if(input.indexOf("exit") >= 0){
                            emiiter.onComplete();
                            sc.close();
                            break;
                        }
                    }
                }).publish();
    }

}
