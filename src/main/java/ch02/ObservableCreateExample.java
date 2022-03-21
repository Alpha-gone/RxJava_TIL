package ch02;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.functions.Consumer;

public class ObservableCreateExample {
    public static void main(String[] args) {
        ObservableCreateExample observableCreateExample = new ObservableCreateExample();

        Observable<Integer> observable = Observable.create(
                (ObservableEmitter<Integer> emitter) -> {
                    emitter.onNext(100);
                    emitter.onNext(200);
                    emitter.onNext(300);
                    emitter.onComplete();
        });

        CommonUtils.divSection("basic");
        observableCreateExample.basic(observable);

        CommonUtils.divSection("subscribeLambda");
        observableCreateExample.subscribeLambda(observable);

        CommonUtils.divSection("subscribeAnonymously");
        observableCreateExample.subscribeAnonymously(observable);

    }

    private void basic(Observable observable){
        observable.subscribe(System.out::println);
    }

    private void subscribeLambda(Observable observable){
        observable.subscribe(data -> System.out.println("Result : " + data));
    }

    private void subscribeAnonymously(Observable observable){
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                System.out.println("Result : " + integer);
            }
        });
    }

}
