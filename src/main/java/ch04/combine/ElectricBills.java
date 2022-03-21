package ch04.combine;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DecimalFormat;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ElectricBills {
    private int idx = 0;

    public static void main(String[] args) {
        ElectricBills electricBills = new ElectricBills();

        CommonUtils.divSection("electricBillV1");
        electricBills.electricBillV1();

        CommonUtils.divSection("electricBillV2");
        electricBills.electricBillV2();
    }

    private void electricBillV1(){
        String[] data = {"100", // 910 + 93.3 * 100 = 10,240원
                         "300" // 1600 + 93.3 * 200 + 197.9 * 100 = 39,050원
        };

        Observable<Integer> basePrice = Observable.fromArray(data)
                .map(Integer::parseInt)
                .map(val -> {
                    if (val <= 200) return 910;
                    if(val <= 400) return 1600;
                    return 7300;
                });

        Observable<Integer> usagePrice = Observable.fromArray(data)
                .map(Integer::parseInt)
                .map(val -> {
                    double series1 = min(200, val)* 93.3;
                    double series2 = min(200, max(val - 200, 0)) * 187.9;
                    double series3 = max(0, max(val - 400, 0)) * 280.65;
                    return (int)(series1 + series2 + series3 );
                });

        Observable<Integer> observable = Observable.zip(
                basePrice,
                usagePrice,
                (baseP, usageP) -> baseP + usageP
        );

        observable.map(val -> new DecimalFormat("#,###").format(val))
                .subscribe(val -> {
                   StringBuilder sb = new StringBuilder();
                   sb.append("Usage: " + data[idx] + "kWh => ");
                   sb.append("Price: " + val + "원");
                   Log.i(sb.toString());

                   idx++;
                });
    }

    private void electricBillV2(){
        String[] data = {"100", // 910 + 93.3 * 100 = 10,240원
                "300" // 1600 + 93.3 * 200 + 197.9 * 100 = 39,050원
        };

        Observable<Integer> basePrice = Observable.fromArray(data)
                .map(Integer::parseInt)
                .map(val -> {
                    if (val <= 200) return 910;
                    if(val <= 400) return 1600;
                    return 7300;
                });

        Observable<Integer> usagePrice = Observable.fromArray(data)
                .map(Integer::parseInt)
                .map(val -> {
                    double series1 = min(200, val)* 93.3;
                    double series2 = min(200, max(val - 200, 0)) * 187.9;
                    double series3 = max(0, max(val - 400, 0)) * 280.65;
                    return (int)(series1 + series2 + series3 );
                });

        Observable<Pair<String, Integer>> observable = Observable.zip(
                basePrice,
                usagePrice,
                Observable.fromArray(data),
                (baseP, usageP, usage) -> Pair.of(usage, baseP + usageP)
        );

        observable.map(val -> Pair.of(val.getLeft(),
                        new DecimalFormat("#,###").format(val.getRight())))
                .subscribe(val -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Usage: " + val.getLeft() + "kWh => ");
                    sb.append("Price: " + val.getRight() + "원");
                    Log.i(sb.toString());
                });
    }
}
