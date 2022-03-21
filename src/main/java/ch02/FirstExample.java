package ch02;

import io.reactivex.rxjava3.core.Observable;

public class FirstExample {
    public void emit(){
        Observable.just(1, 2, 3, 4, 5, 6).subscribe(System.out::println);
    }

    public static void main(String[] args) {
        FirstExample firstExample = new FirstExample();
        firstExample.emit();
    }
}
