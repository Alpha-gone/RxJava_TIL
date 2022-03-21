package ch02;

import common.CommonUtils;
import common.Order;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class ObservableFromCallable {
    public static void main(String[] args) {
      ObservableFromCallable observableFromCallable = new ObservableFromCallable();

      CommonUtils.divSection("withLambda");
      observableFromCallable.withLambda();

      CommonUtils.divSection("withOutLambda");
      observableFromCallable.withOutLambda();
    }

    private void withLambda(){
        Callable<String> callable = () ->{
            Thread.sleep(1000);
            return "Hello Callable";
        };

        observableSubscribe(callable);
    }

    private void withOutLambda(){
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "Hello Callable With Out Lambda";
            }
        };

        observableSubscribe(callable);
    }

    private void observableSubscribe(Callable callable){
        Observable<String> observable = Observable.fromCallable(callable);

        observable.subscribe(System.out::println);
    }
}
