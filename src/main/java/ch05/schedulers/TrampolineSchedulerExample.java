package ch05.schedulers;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrampolineSchedulerExample {
    public static void main(String[] args) {
        TrampolineSchedulerExample trampolineSchedulerExample = new TrampolineSchedulerExample();

        CommonUtils.divSection("basic");
        trampolineSchedulerExample.basic();
    }

    private void basic(){
        String[] orgs = {"1", "3", "5"};
        Observable<String> observable = Observable.fromArray(orgs);

        observable.subscribeOn(Schedulers.trampoline())
                .map(data -> "<<" + data + ">>")
                .subscribe(Log::i);

        CommonUtils.sleep(10);

        observable.subscribeOn(Schedulers.trampoline())
                .map(data -> "##" + data + "##")
                .subscribe(Log::i);

        CommonUtils.sleep(500);
    }
}
