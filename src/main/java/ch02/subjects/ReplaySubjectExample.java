package ch02.subjects;

import common.CommonUtils;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class ReplaySubjectExample {
    public static void main(String[] args) {
        ReplaySubjectExample replaySubjectExample = new ReplaySubjectExample();

        CommonUtils.divSection("marbleDiagram");
        replaySubjectExample.marbleDiagram();
    }

    private void marbleDiagram(){
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));

        subject.onNext("1");
        subject.onNext("3");

        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));

        subject.onNext("5");
        subject.onComplete();
    }
}
