package ch07;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

public class Exceptionhandling {
    public static void main(String[] args) {
        Exceptionhandling exceptionhandling = new Exceptionhandling();

        CommonUtils.divSection("cannotCatch");
        exceptionhandling.cannotCatch();
    }

    private void cannotCatch(){
        Observable<String> observable = Observable.create(
                emitter -> {
                    emitter.onNext("1");
                    emitter.onError(new Exception("Some Error"));
                    emitter.onNext("3");
                    emitter.onComplete();
                }
        );

        try {
            observable.subscribe(Log::i);
        }catch (Exception e){
            Log.e(e.getMessage());
        }
    }
}
