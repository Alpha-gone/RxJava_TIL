package ch07;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;

import java.util.concurrent.TimeUnit;

public class DoOnExample {
    public static void main(String[] args) {
        DoOnExample doOnExample = new DoOnExample();

        CommonUtils.divSection("basic");
        doOnExample.basic();

        //에러 발생
        /*CommonUtils.divSection("withError");
        doOnExample.withError();*/

        CommonUtils.divSection("doOnEach");
        doOnExample.doOnEach();

        CommonUtils.divSection("doOnEachObserver");
        doOnExample.doOnEachObserver();

        CommonUtils.divSection("doOnSubscribeAndDispose");
        doOnExample.doOnSubscriveAndDispose();

        CommonUtils.divSection("doOnLifecycle");
        doOnExample.doOnLifecycle();

        CommonUtils.divSection("doOnTerminate");
        doOnExample.doOnTerminate();

    }

    private void basic(){
        String[] orgs = {"1", "3", "5"};
        Observable<String> source = Observable.fromArray(orgs);

        source.doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete(() -> Log.d("onComplete"))
                .doOnError(e -> Log.e("onError", e.getMessage()))
                .subscribe(Log::i);
    }

    private void withError(){
        Integer[] divider = {10, 5, 0};

        Observable.fromArray(divider)
                .map(div -> 1000 / div)
                .doOnNext(data -> Log.d("onNext()", data))
                .doOnComplete(() -> Log.d("onComplete"))
                .doOnError(e -> Log.e("onError", e.getMessage()))
                .subscribe(Log::i);
    }

    private void doOnEach(){
        String[] data = {"ONE", "TWO", "THREE"};
        Observable<String> observable = Observable.fromArray(data);

        observable.doOnEach(noti -> {
            if (noti.isOnNext()) Log.d("onNext()", noti.getValue());
            if (noti.isOnComplete()) Log.d("onComplete");
            if (noti.isOnError()) Log.e("onError", noti.getError().getMessage());
        }).subscribe(Log::i);
    }

    private void doOnEachObserver(){
        String[] orgs = {"1", "3", "5"};
        Observable<String> observable = Observable.fromArray(orgs);

        observable.doOnEach(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d("onNext()", s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("onError", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("onComplete()");
            }
        }).subscribe(Log::i);
    }

    private void doOnSubscriveAndDispose(){
        String[] orgs = {"1", "3", "5", "2", "6"};
        Observable<String> observable = Observable.fromArray(orgs)
                .zipWith(Observable.interval(100L, TimeUnit.MICROSECONDS), (a, b) -> a)
                .doOnSubscribe(d -> Log.d("onSubscribe()"))
                .doOnDispose(() -> Log.d("onDispose()"));

        Disposable d = observable.subscribe(Log::i);

        CommonUtils.sleep(200);
        d.dispose();
        CommonUtils.sleep(300);
    }

    private void doOnLifecycle(){
        String[] orgs = {"1", "3", "5", "2", "6"};
        Observable<String> observable = Observable.fromArray(orgs)
                .zipWith(Observable.interval(100L, TimeUnit.MICROSECONDS), (a, b) -> a)
                .doOnLifecycle(d -> Log.d("onSubscribe()"), () -> Log.d("onDispose()"));

        Disposable d = observable.subscribe(Log::i);

        CommonUtils.sleep(200);
        d.dispose();
        CommonUtils.sleep(300);
    }

    private void doOnTerminate(){
        String[] orgs= {"1", "3", "5"};
        Observable<String> observable = Observable.fromArray(orgs);

        observable.doOnTerminate(() -> Log.d("onTerminate()"))
                .doOnComplete(() -> Log.d("onComplete()"))
                .doOnError(e -> Log.e("onError()", e.getMessage()))
                .subscribe(Log::i);
    }
}
