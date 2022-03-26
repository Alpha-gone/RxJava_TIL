package ch05.schedulers;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorSchedulerExample {
    public static void main(String[] args) {
        ExecutorSchedulerExample executorSchedulerExample = new ExecutorSchedulerExample();

        CommonUtils.divSection("basic");
        executorSchedulerExample.basic();
    }

    private void basic(){
        final int THREAD_NUM = 10;

        String[] data = {"1", "3", "5"};
        Observable<String> observable = Observable.fromArray(data);
        Executor executor = Executors.newFixedThreadPool(THREAD_NUM);

        observable.subscribeOn(Schedulers.from(executor))
                .subscribe(Log::i);

        CommonUtils.sleep(10);

        observable.subscribeOn(Schedulers.from(executor))
                .subscribe(Log::i);

        CommonUtils.sleep(500);
    }
}
