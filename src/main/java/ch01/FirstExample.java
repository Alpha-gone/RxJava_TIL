package ch01;

import io.reactivex.rxjava3.core.Observable;

public class FirstExample {
    public void emit(){
        Observable.just("Hello", "RxJava 2!!").subscribe(System.out::println);
    }

    public static void main(String[] args) {
        FirstExample firstExample = new FirstExample();
        firstExample.emit();
    }
}
