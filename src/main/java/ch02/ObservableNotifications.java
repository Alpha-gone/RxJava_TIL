package ch02;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class ObservableNotifications {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just("RED", "GREEN", "YELLOW");

        Disposable d = observable.subscribe(
                v -> System.out.println("onNext() : value :  " + v),
                err -> System.out.println("onError() : err : " + err.getMessage()),
                () -> System.out.println("onComplete()")
        );

        System.out.println("isDisposed() : " + d.isDisposed());

    }
}
