package ch04.conditional;

import common.CommonUtils;
import common.Log;
import common.Shape;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AllFunctionExample {
    public static void main(String[] args) {
        AllFunctionExample allFunctionExample = new AllFunctionExample();
        String[] dataTrue = {"1", "2", "3", "4"};
        String[] dataFalse = {"1", "2-P", "3", "4"};

        CommonUtils.divSection("marbleDiagram - True");
        allFunctionExample.marbleDiagram(dataTrue);

        CommonUtils.divSection("marbleDiagram - False");
        allFunctionExample.marbleDiagram(dataFalse);
    }

    private void marbleDiagram(String[] array){
        Single<Boolean> single = Observable.fromArray(array)
                .map(Shape::getShape)
                .all(Shape.BALL::equals);

        single.subscribe(val -> Log.i(val));
    }
}
