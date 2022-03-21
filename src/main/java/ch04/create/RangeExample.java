package ch04.create;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

public class RangeExample {
    public static void main(String[] args) {
        RangeExample rangeExample = new RangeExample();

        CommonUtils.divSection("forLoop");
        rangeExample.forLoop();
    }

    private void forLoop(){
        Observable<Integer> observable = Observable.range(1, 10)
                .filter(num -> num % 2 == 0);
        observable.subscribe(Log::i);
    }
}
