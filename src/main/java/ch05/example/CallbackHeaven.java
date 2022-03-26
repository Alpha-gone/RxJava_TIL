package ch05.example;

import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class CallbackHeaven {
    private static final String FIRST_URL = "https://api.github.com/zen";
    private static final String SECOND_URL  = CommonUtils.GITHUB_ROOT + "samples/callback_hell";

    private final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
            CallbackHeaven callbackHeaven = new CallbackHeaven();

            CommonUtils.divSection("usingConcat");
            callbackHeaven.usingConcat();

            CommonUtils.divSection("usingZip");
            callbackHeaven.usingZip();
    }

    private void usingConcat(){
        CommonUtils.exampleStart();

        Observable<String> observable = Observable.just(FIRST_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get)
                .concatWith(Observable.just(SECOND_URL)
                        .map(OkHttpHelper::get));

        observable.subscribe(Log::it);
        CommonUtils.sleep(5000);
    }

    private void usingZip(){
        CommonUtils.exampleStart();

        Observable<String> first = Observable.just(FIRST_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);

        Observable<String> second = Observable.just(SECOND_URL)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get);

        Observable.zip(first, second, (fir, sec) -> "\n>>" + fir + "\n>>" + sec)
                .subscribe(Log::it);
        CommonUtils.sleep(5000);
    }
}
