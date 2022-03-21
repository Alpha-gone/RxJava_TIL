package ch04.create;

import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class RepeatExample {
    public static void main(String[] args) {
        RepeatExample repeatExample = new RepeatExample();

        CommonUtils.divSection("marbleDiagram");
        repeatExample.marbleDiagram();

        CommonUtils.divSection("heartBeatV1");
        repeatExample.heartBeatV1();
    }

    private void marbleDiagram(){
        String[] balls = {"1", "3", "5"};
        Observable<String> observable = Observable.fromArray(balls).repeat(3);

        observable.doOnComplete(() -> Log.d("onComplete")).subscribe(Log::i);
    }

    private void heartBeatV1(){
        CommonUtils.exampleStart();
        String serverUrl = "https://api.github.com/zen";

        Observable.timer(2, TimeUnit.SECONDS)
                .map(val -> serverUrl)
                .map(OkHttpHelper::get)
                .repeat()
                .subscribe(res -> Log.it("Ping Result : " + res));

        CommonUtils.sleep(10000);
    }
}
