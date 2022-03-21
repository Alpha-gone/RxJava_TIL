package ch02.subjects;

import common.CommonUtils;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class PublishSubjectExample {
    public static void main(String[] args) {
        PublishSubjectExample publishSubjectExample = new PublishSubjectExample();

        CommonUtils.divSection("marbleDiagram");
        publishSubjectExample.marbleDiagram();

    }

    private void marbleDiagram(){
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));

        subject.onNext("1");
        subject.onNext("3");

        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));

        subject.onNext("5");
        subject.onComplete();
    }
}
