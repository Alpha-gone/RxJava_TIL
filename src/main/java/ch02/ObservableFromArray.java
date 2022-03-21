package ch02;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ObservableFromArray {
    public static void main(String[] args) {
        ObservableFromArray observableFromArray = new ObservableFromArray();
        Integer[] arrInteger = {100, 200, 300};
        int[] arrInt = {400, 500, 600};

        CommonUtils.divSection("Integer Array");
        observableFromArray.observableSubscribe(arrInteger);

        CommonUtils.divSection("Int Array Wrong");
        observableFromArray.observableSubscribe(arrInt);

        CommonUtils.divSection("Int Array");
        observableFromArray.observableSubscribe(toIntegerArray(arrInt));

    }

    private void observableSubscribe(Integer[] arrInteger){
        Observable<Integer> observable = Observable.fromArray(arrInteger);
        observable.subscribe(System.out::println);
    }

    private void observableSubscribe(int[] arrInt){
        Observable.fromArray(arrInt).subscribe(System.out::println);
    }

    //int 배열을 Integer배열로 변환
    private static Integer[] toIntegerArray(int[] intArray){
        return IntStream.of(intArray).boxed().toArray(Integer[]::new);
    }
}
