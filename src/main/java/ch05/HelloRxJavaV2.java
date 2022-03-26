package ch05;

import common.Log;
import io.reactivex.rxjava3.core.Observable;

public class HelloRxJavaV2 {
    public static void main(String[] args) {
        HelloRxJavaV2 helloRxJavaV2 = new HelloRxJavaV2();
        helloRxJavaV2.emit();
    }

    public void emit(){
        Observable.just("Hello", "RxJava 2!!").subscribe(Log::i);
    }
}
