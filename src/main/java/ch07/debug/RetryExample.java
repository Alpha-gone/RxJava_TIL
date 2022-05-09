package ch07.debug;

import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class RetryExample {
    public static void main(String[] args) {
        RetryExample retryExample = new RetryExample();
        String url = "https://api.github.com/zen";

        CommonUtils.divSection("retry5");
        retryExample.retry5(url);

        CommonUtils.divSection("retryWithDelay");
        retryExample.retryWithDelay(url);

        CommonUtils.divSection("retryUntil");
        retryExample.retryUntil(url);

        CommonUtils.divSection("retryWhen");
        retryExample.retryWhen();
    }

    private void retry5(String url){
        CommonUtils.exampleStart();

        Observable<String> observable = Observable.just(url)
                .map(OkHttpHelper::getT)
                .retry(5)
                .onErrorReturn(e -> CommonUtils.ERROR_CODE);

        observable.subscribe(data -> Log.it("result : " + data));
    }

    private void retryWithDelay(String url){
        final int RETRY_MAX = 5;
        final int RETRY_DELAY = 1000;

        CommonUtils.exampleStart();

        Observable<String> observable = Observable.just(url)
                .map(OkHttpHelper::getT)
                .retry((retryCnt, e) -> {
                        Log.e("retryCnt = " + retryCnt);
                        CommonUtils.sleep(RETRY_DELAY);

                        return retryCnt < RETRY_MAX;
                    })
                .onErrorReturn(e -> CommonUtils.ERROR_CODE);

        observable.subscribe(data -> Log.it("result : " + data));

    }

    private void retryUntil(String url){
        CommonUtils.exampleStart();

        Observable<String> observable = Observable.just(url)
                .map(OkHttpHelper::getT)
                .subscribeOn(Schedulers.io())
                .retryUntil(() -> {
                    if(CommonUtils.isNetworkAvailable()){
                        return true;
                    }

                    CommonUtils.sleep(1000);
                    return false;
                });

        observable.subscribe(Log::i);
    }

    private void retryWhen(){
        Observable.create((ObservableEmitter<String> emitter) -> {
            emitter.onError(new RuntimeException("always fails"));
        }).retryWhen(attempts -> {
            return attempts.zipWith(Observable.range(1, 3), (n, i) -> i)
                    .flatMap(i -> {
                        Log.d("delay retry by " + i + " seconds");
                        return Observable.timer(i, TimeUnit.SECONDS);
                    });
        }).blockingForEach(Log::d);
    }

}
