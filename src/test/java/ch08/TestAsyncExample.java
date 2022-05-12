package ch08;

import common.GsonHelper;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class TestAsyncExample {

    @DisplayName("test Observable.interval() wrong")
    @Test
    @Disabled
    void testIntervalWrongWay(){
        Observable<Integer> observable = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(Long::intValue);

        observable.doOnNext(Log::d)
                .test().assertResult(0, 1, 2, 3, 4);
    }

    @DisplayName("test Observable.interval() wrong")
    @Test
    void testInterval(){
        Observable<Integer> observable = Observable.interval(100L, TimeUnit.MICROSECONDS)
                .take(5)
                .map(Long::intValue);

        observable.doOnNext(Log::d)
                .test()
                .awaitDone(1L, TimeUnit.SECONDS)
                .assertResult(0, 1, 2 ,3 ,4);
    }

    @DisplayName("testHttp() example")
    @Test
    void testHttp(){
        final String url = "https://api.github.com/users/yudong80";
        Observable<String> observable = Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(OkHttpHelper::get)
                .doOnNext(Log::d)
                .map(json -> GsonHelper.parseValue(json, "name"))
                .observeOn(Schedulers.newThread());

        String expected = "Dong Hwan Yu";

        observable.doOnNext(Log::i)
                .test()
                .awaitDone(3, TimeUnit.SECONDS)
                .assertResult(expected);
    }
}
