package ch05.schedulers;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.File;

public class IOSchedulerExample {
    public static void main(String[] args) {
        IOSchedulerExample ioSchedulerExample = new IOSchedulerExample();

        CommonUtils.divSection("basic");
        ioSchedulerExample.basic();
    }

    private void basic(){
        String root ="C:\\";
        File[] files = new File(root).listFiles();

        Observable<String> observable = Observable.fromArray(files)
                .filter(f -> !f.isDirectory())
                .map(f -> f.getAbsolutePath())
                .subscribeOn(Schedulers.io());

        observable.subscribe(Log::i);
        CommonUtils.sleep(500);
    }
}
