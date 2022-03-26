package ch05.schedulers;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ComputationSchedulerExample {
    public static void main(String[] args) {
        ComputationSchedulerExample computationSchedulerExample = new ComputationSchedulerExample();

        CommonUtils.divSection("basic");
        computationSchedulerExample.basic();
    }

    private void basic(){
        String[] orgs = {"1", "3", "5"};

        Observable<String> observable = Observable.fromArray(orgs)
                .zipWith(Observable.interval(100L, TimeUnit.MILLISECONDS), (a, b) -> a);

        observable.map(item -> "<<" + item + ">>")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);

        CommonUtils.sleep(10);

        observable.map(item -> "##" + item + "##")
                .subscribeOn(Schedulers.computation())
                .subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
