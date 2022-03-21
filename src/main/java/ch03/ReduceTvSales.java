package ch03;

import common.CommonUtils;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ReduceTvSales {
    public static void main(String[] args) {
        ReduceTvSales reduceTvSales = new ReduceTvSales();

        CommonUtils.divSection("run");
        reduceTvSales.run();
    }

    private void run(){
        List<Pair<String, Integer>> sales = new ArrayList<>();

        sales.add(Pair.of("TV", 2500));
        sales.add(Pair.of("Camera", 300));
        sales.add(Pair.of("TV", 1600));
        sales.add(Pair.of("Phone", 800));

        Maybe<Integer> maybe = Observable.fromIterable(sales).filter(sale -> "TV".equals(sale.getLeft()))
                .map(sale -> sale.getRight()).reduce((sale1, sale2) -> sale1 + sale2);

        maybe.subscribe(tot -> System.out.println("TV Sales: $" + tot));
    }
}
