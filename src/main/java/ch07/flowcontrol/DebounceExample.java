package ch07.flowcontrol;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class DebounceExample {
    public static void main(String[] args) {
        DebounceExample debounceExample = new DebounceExample();

        CommonUtils.divSection("marbleDiagram");
        debounceExample.marbleDiagram();
    }

    private void marbleDiagram(){
        String[] data = {"1", "2", "3", "5"};

        Observable<String> observable = Observable.concat(
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[0]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[1]),
                Observable.timer(100L, TimeUnit.MILLISECONDS).map(i -> data[2]),
                Observable.timer(300L, TimeUnit.MILLISECONDS).map(i -> data[3]))
                .debounce(200L, TimeUnit.MILLISECONDS);

        observable.subscribe(Log::i);
        CommonUtils.sleep(1000);
    }
}
