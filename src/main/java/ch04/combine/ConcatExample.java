package ch04.combine;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;

import java.util.concurrent.TimeUnit;

public class ConcatExample {
    public static void main(String[] args) {
        ConcatExample concatExample = new ConcatExample();

        CommonUtils.divSection("marbleDiagram");
        concatExample.marbleDiagram();
    }

    private void marbleDiagram(){
        Action onCompleteAction = () -> Log.d("onComplete()");

        String[] firstData = {"1", "3", "5"};
        String[] secondData = {"2", "4", "6"};

        Observable<String> firstSource = Observable.fromArray(firstData).doOnComplete(onCompleteAction);

        //Observable<String> secondSource = Observable.fromArray(secondData).doOnComplete(onCompleteAction);
        Observable<String> secondSource = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> secondData[idx])
                .take(secondData.length)
                .doOnComplete(onCompleteAction);

        Observable<String> observable = Observable.concat(firstSource, secondSource).doOnComplete(onCompleteAction);

        observable.subscribe(Log::i);
        CommonUtils.sleep(1000);

    }
}
