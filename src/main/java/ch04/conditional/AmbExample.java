package ch04.conditional;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmbExample {
    public static void main(String[] args) {
        AmbExample ambExample = new AmbExample();

        CommonUtils.divSection("marbleDiagram");
        ambExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] firstData = {"1", "3", "5"};
        String[] secondData = {"2-R", "4-R"};

        List<Observable<String>> list = Arrays.asList(
                Observable.fromArray(firstData).doOnComplete(() -> Log.d("Observable #1: onComplete()")),
                Observable.fromArray(secondData).delay(100L, TimeUnit.MILLISECONDS)
                        .doOnComplete(() -> Log.d("Observable #2: onComplete()"))
        );

        Observable.amb(list)
                .doOnComplete(() -> Log.d("Result: onComplete()"))
                .subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
