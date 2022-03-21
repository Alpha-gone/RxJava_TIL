package ch02;

import common.CommonUtils;
import common.Order;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SingleExample {
    public static void main(String[] args) {
        SingleExample singleExample = new SingleExample();

        CommonUtils.divSection("Just");
        singleExample.just();

        CommonUtils.divSection("fromObservable");
        singleExample.fromObservable();

        CommonUtils.divSection("errorCase");
        singleExample.errorCase();
    }

    private void just(){
        Single<String> single = Single.just("Hello Single");
        single.subscribe(System.out::println);
    }

    private void fromObservable(){
        //1. Observable에서 Singble 객체로 변환
        Observable<String> observable = Observable.just("Hello Single");
        Single.fromObservable(observable).subscribe(System.out::println);

        //2. single() 함수를 호출해 Single 객체 생성
        Observable.just("Hello Single").single("default item").subscribe(System.out::println);

        //3. first() 함수를 호출해 Single 객체 생성
        String[] colors = {"Red", "Blue", "Gold"};
        Observable.fromArray(colors).first("default value").subscribe(System.out::println);

        //4. empty Observable에서 Single 객체 생성
        Observable.empty().single("default value").subscribe(System.out::println);

        //5. take() 함수에서 Single 객체 생성
        Observable.just(new Order("ORD-1"), new Order("ORD-2"))
                .take(1).single(new Order("default order"))
                .subscribe(System.out::println);
    }

    private void errorCase(){
        Single<String> single = Observable.just("Hello Single", "Error").single("default item");
        single.subscribe(System.out::println);
    }
}
