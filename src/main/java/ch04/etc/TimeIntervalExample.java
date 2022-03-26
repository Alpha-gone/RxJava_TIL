package ch04.etc;

import common.CommonUtils;
import common.Log;
import common.MarbleDiagram;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Timed;

public class TimeIntervalExample {
    public static void main(String[] args) {
        TimeIntervalExample timeIntervalExample = new TimeIntervalExample();

        CommonUtils.divSection("marbleDiagram");
        timeIntervalExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] data = {"1", "3", "7"};

        CommonUtils.exampleStart();

        Observable<Timed<String>> observable = Observable.fromArray(data)
                .delay(item -> {
                    CommonUtils.doSomething();
                    return Observable.just(item);
                })
                .timeInterval();

        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

}
