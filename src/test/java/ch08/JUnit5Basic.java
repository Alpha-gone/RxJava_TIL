package ch08;

import static org.junit.jupiter.api.Assertions.assertEquals;

import common.Log;
import common.Shape;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JUnit5Basic {
    @DisplayName("JUnit 5 First Example")
    @Test
    void testFirst(){
        int expected = 3;
        int actual = 1 + 2;
        assertEquals(expected, actual);
    }

    @DisplayName("test getShape() Observable")
    @Test
    void testGetShapeObservable(){
        String[] data = {"1", "2-R", "3-T"};

        Observable<String> observable = Observable.fromArray(data)
                .map(Shape::getShape);

        String[] expected = {Shape.BALL, Shape.RECTANGLE, Shape.TRIANGLE};
        List<String> actual = new ArrayList<>();

        observable.doOnNext(Log::d)
                .subscribe(actual::add);

        assertEquals(Arrays.asList(expected), actual);
    }
}
