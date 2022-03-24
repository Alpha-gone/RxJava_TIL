package ch04.etc;

import common.CommonUtils;
import common.Log;
import hu.akarnokd.rxjava3.math.MathFlowable;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MathFunctionExample {
    public static void main(String[] args) {
        MathFunctionExample mathFunctionExample = new MathFunctionExample();

        CommonUtils.divSection("marbleDiagram");
        mathFunctionExample.marbleDiagram();
    }

    private void marbleDiagram(){
        Integer[] data = {1, 2, 3, 4};

        Single<Long> single = Observable.fromArray(data).count();
        single.subscribe(count -> Log.i("Count is " + count));

        Flowable.fromArray(data).to(MathFlowable::max)
                .subscribe(max -> Log.i("Max is " + max));

        Flowable.fromArray(data).to(MathFlowable::min)
                .subscribe(min -> Log.i("Min is " + min));

        Flowable<Integer> flowableSumInt = Flowable.fromArray(data)
                .to(MathFlowable::sumInt);
        flowableSumInt.subscribe(sum -> Log.i("Sum is " + sum));

        Flowable<Double> flowableAverageDouble = Observable.fromArray(data)
                .toFlowable(BackpressureStrategy.BUFFER)
                .to(MathFlowable::averageDouble);
        flowableAverageDouble.subscribe(avg -> Log.i("Avg is " + avg));
    }
}
