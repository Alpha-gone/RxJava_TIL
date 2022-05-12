package ch08;

import common.Shape;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.observers.TestObserver;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestObserverExample {
    @DisplayName("#1: using TestObserver for Shape.getShape()")
    @Test
    void testGetShapeUsingTestObserver(){
        String[] data = {"1", "2-R", "3-T"};
        Observable<String> observable = Observable.fromArray(data)
                .map(Shape::getShape);

        String[] expected = {Shape.BALL, Shape.RECTANGLE, Shape.TRIANGLE};
        observable.test().assertResult(expected);
    }

    @DisplayName("assertFailure() example")
    @Test
    void assertFailureExample(){
        String[] data = {"100", "200", "%300"};
        Observable<Integer> observable = Observable.fromArray(data)
                .map(Integer::parseInt);

        observable.test().assertFailure(NumberFormatException.class, 100, 200);
    }

    //assertFailureAndMessage 메소드 없음
    @DisplayName("assertFailureAndMessage() example")
    @Test
    @Disabled
    void assertFailureAndMessage(){
        String[] data = {"100", "200", "%300"};
        Observable<Integer> source = Observable.fromArray(data)
                .map(Integer::parseInt);

        //source.test().assertFailureAndMessage(NumberFormatException.class, "For input String : \"%300\"", 100, 200);
    }

    @DisplayName("assertComplete() example")
    @Test
    void assertComplete(){
        Observable<String> observable = Observable.create(
                (ObservableEmitter<String> emitter) ->{
                    emitter.onNext("Hello RxJava");
                    emitter.onComplete();
                }
        );

        observable.test().assertComplete();
    }
}
