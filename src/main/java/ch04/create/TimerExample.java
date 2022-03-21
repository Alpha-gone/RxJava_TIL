package ch04.create;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimerExample {
    public static void main(String[] args) {
        TimerExample timerExample = new TimerExample();

        CommonUtils.divSection("showTime");
        timerExample.showTime();
    }

    private void showTime(){
        CommonUtils.exampleStart();

        Observable<String> observable = Observable.timer(500L, TimeUnit.MILLISECONDS)
                .map(notUsed ->{
                    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
                });

        observable.subscribe(Log::it);
        CommonUtils.sleep(1000);
    }

}
