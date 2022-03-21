package ch02;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class ConnnetableObservableExample {
    public static void main(String[] args) {
        ConnnetableObservableExample connnetableObservableExample = new ConnnetableObservableExample();

        CommonUtils.divSection("marbleDiagram");
        connnetableObservableExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] dt = {"1", "3", "5"};
        Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> dt[i])
                .take(dt.length);
        ConnectableObservable<String> observable = balls.publish();

        observable.subscribe(data -> System.out.println("Subscriber #1 => " + data));
        observable.subscribe(data -> System.out.println("Subscriber #2 => " + data));
        observable.connect();

        CommonUtils.sleep(250);
        observable.subscribe(data -> System.out.println("Subscriber #3 => " + data));
        CommonUtils.sleep(100);

    }
}
