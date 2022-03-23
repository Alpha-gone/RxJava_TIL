package ch04.combine;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class MergeExample {
    public static void main(String[] args) {
        MergeExample mergeExample = new MergeExample();

        CommonUtils.divSection("marbleDiagram");
        mergeExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] firstData = {"1", "3"};
        String[] secondData = {"2", "4", "6"};

        Observable<String> firstSource = Observable.interval(0L, 100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> firstData[idx])
                .take(firstData.length);

        Observable<String> secondSource = Observable.interval(50L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> secondData[idx])
                .take(secondData.length);

        Observable<String> observable = Observable.merge(firstSource, secondSource);
        observable.subscribe(Log::i);

        CommonUtils.sleep(1000);
    }
}
