package ch05.schedulers;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewThreadScheldulerExample {
    public static void main(String[] args) {
        NewThreadScheldulerExample newThreadScheldulerExample = new NewThreadScheldulerExample();

        CommonUtils.divSection("basic");
        newThreadScheldulerExample.basic();
    }

    private void basic(){
        String[] objs = {"1", "3", "5"};

        Observable.fromArray(objs)
                .doOnNext(data -> Log.v("Original data = " + data))
                .map(data -> "<<" + data + ">>")
                .subscribeOn(Schedulers.newThread())
                .subscribe(Log::i);

        CommonUtils.sleep(500);

        Observable.fromArray(objs)
                .doOnNext(data -> Log.v("Original data = " + data))
                .map(data -> "##" + data + "##")
                .subscribeOn(Schedulers.newThread())
                .subscribe(Log::i);

        CommonUtils.sleep(500);
    }
}
