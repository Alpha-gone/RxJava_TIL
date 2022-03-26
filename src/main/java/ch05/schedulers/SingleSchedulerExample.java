package ch05.schedulers;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SingleSchedulerExample {
    public static void main(String[] args) {
        SingleSchedulerExample singleSchedulerExample = new SingleSchedulerExample();

        CommonUtils.divSection("basic");
        singleSchedulerExample.basic();
    }

    private void basic(){
        Observable<Integer> numbers = Observable.range(100, 5);
        Observable<String> chars = Observable.range(0, 5)
                .map(CommonUtils::numberToAlphabet);

        numbers.subscribeOn(Schedulers.single())
                .subscribe(Log::i);

        CommonUtils.sleep(10);

        chars.subscribeOn(Schedulers.single())
                .subscribe(Log::i);

        CommonUtils.sleep(500);

    }
}
