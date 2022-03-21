package ch02;

import common.CommonUtils;
import common.Order;
import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ObservableFromIterable {
    public static void main(String[] args) {
        ObservableFromIterable observableFromIterable = new ObservableFromIterable();

        CommonUtils.divSection("withOutLambda");
        observableFromIterable.listExample();

        CommonUtils.divSection("setExample");
        observableFromIterable.setExample();

        CommonUtils.divSection("blockingQueueExample");
        observableFromIterable.blockingQueueExample();
    }

    private void listExample(){
        List<String> names = new ArrayList<>();

        names.add("Jerry");
        names.add("William");
        names.add("Bob");

       observableSubscribe(names);
    }

    private void setExample(){
        Set<String> cities = new HashSet<>();

        cities.add("Seoul");
        cities.add("London");
        cities.add("Paris");

        observableSubscribe(cities);
    }

    private void blockingQueueExample(){
        BlockingQueue<Order> orderBlockingQueue = new ArrayBlockingQueue<>(100);

        orderBlockingQueue.add(new Order("ORD-1"));
        orderBlockingQueue.add(new Order("ORD-2"));
        orderBlockingQueue.add(new Order("ORD-3"));

        observableSubscribe(orderBlockingQueue);
    }

    private void observableSubscribe(Iterable iterable){
        Observable<String> observable = Observable.fromIterable(iterable);
        observable.subscribe(System.out::println);
    }

    private void observableSubscribe(BlockingQueue iterable){
        Observable<Order> observable = Observable.fromIterable(iterable);
        observable.subscribe(order -> System.out.println(order.getId()));
    }
}
