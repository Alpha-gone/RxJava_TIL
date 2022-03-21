package ch02.subjects;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class AsyncSubjectExample {
    public static void main(String[] args) {
        AsyncSubjectExample asyncSubjectExample = new AsyncSubjectExample();

        CommonUtils.divSection("marbleDiagram");
        asyncSubjectExample.marbleDiagram();

        CommonUtils.divSection("asSubscriber");
        asyncSubjectExample.asSubscriber();

        CommonUtils.divSection("afterComplete");
        asyncSubjectExample.afterComplete();

    }

    private void marbleDiagram(){
        AsyncSubject<String> subject = AsyncSubject.create();

        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));

        subject.onNext("1");
        subject.onNext("3");

        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));

        subject.onNext("5");
        subject.onComplete();
    }

    private void asSubscriber(){
        Float[] temperature = {10.1f, 13.4f, 12.5f};
        Observable<Float> observable = Observable.fromArray(temperature);

        AsyncSubject<Float> subject = AsyncSubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));

        observable.subscribe(subject);
    }

    private void afterComplete(){
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();

        asyncSubject.onNext(10);
        asyncSubject.onNext(11);

        asyncSubject.subscribe(data -> System.out.println("Subscriber #1 => " + data) );

        asyncSubject.onNext(12);
        asyncSubject.onComplete();
        asyncSubject.onNext(13);

        asyncSubject.subscribe(data -> System.out.println("Subscriber #2 => " + data) );
        asyncSubject.subscribe(data -> System.out.println("Subscriber #3 => " + data) );

    }
}
