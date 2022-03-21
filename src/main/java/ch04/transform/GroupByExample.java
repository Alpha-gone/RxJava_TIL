package ch04.transform;


import common.CommonUtils;
import common.Shape;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.GroupedObservable;

public class GroupByExample {
    public static void main(String[] args) {
        GroupByExample groupByExample = new GroupByExample();

        CommonUtils.divSection("marbleDiagram");
        groupByExample.marbleDiagram();

        CommonUtils.divSection("filterBallGroup");
        groupByExample.filterBallGroup();
    }

    private void marbleDiagram(){
        String[] objs = {"6", "4", "2-T", "2", "6-T", "4-T"};
        Observable<GroupedObservable<String, String>> observable = Observable.fromArray(objs)
                .groupBy(Shape::getShape);

        observable.subscribe(obj -> {
            obj.subscribe(val -> System.out.println("GROUP:" + obj.getKey() + "\t Value:" + val));
        });
    }

    private void filterBallGroup(){
        String[] objs = {"6", "4", "2-T", "2", "6-T", "4-T"};
        Observable<GroupedObservable<String, String>> observable = Observable.fromArray(objs)
                .groupBy(Shape::getShape);

        observable.subscribe(obj -> {
            obj.filter(val -> obj.getKey().equals(Shape.BALL))
                .subscribe(val -> System.out.println("GROUP:" + obj.getKey() + "\t Value:" + val));
        });
    }
}
