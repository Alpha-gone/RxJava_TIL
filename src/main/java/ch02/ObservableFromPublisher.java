package ch02;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class ObservableFromPublisher {
    public static void main(String[] args) {
        ObservableFromPublisher observableFromPublisher = new ObservableFromPublisher();

        CommonUtils.divSection("withLambda");
        observableFromPublisher.withLambda();

        CommonUtils.divSection("withOutLambda");
        observableFromPublisher.withOutLambda();

    }

    private void withLambda(){
        Publisher<String> publisher = (Subscriber<? super String > s) -> {
            s.onNext("Hello Observable.fromPublisher()");
            s.onComplete();
        };

        observableSubscribe(publisher);
    }

    private void withOutLambda(){
        Publisher<String> publisher = new Publisher<String>() {
            @Override
            public void subscribe(Subscriber<? super String> s) {
                s.onNext("Hello Observable.fromPublisher()");
                s.onComplete();
            }
        };

        observableSubscribe(publisher);
    }

    private void observableSubscribe(Publisher publisher){
        Observable<String> observable = Observable.fromPublisher(publisher);
        observable.subscribe(System.out::println);
    }
}
