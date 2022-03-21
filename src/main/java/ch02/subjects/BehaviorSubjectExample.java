package ch02.subjects;

import common.CommonUtils;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class BehaviorSubjectExample {
    public static void main(String[] args) {
        BehaviorSubjectExample behaviorSubjectExample = new BehaviorSubjectExample();

        CommonUtils.divSection("marbleDiagram");
        behaviorSubjectExample.marbleDiagram();
    }

    private void marbleDiagram(){
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
        subject.subscribe(data -> System.out.println("Subscriber #1 => " + data));

        subject.onNext("1");
        subject.onNext("3");

        subject.subscribe(data -> System.out.println("Subscriber #2 => " + data));

        subject.onNext("5");
        subject.onComplete();
    }
}
