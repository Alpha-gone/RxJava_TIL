package ch07;

import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Exceptionhandling {
    public static void main(String[] args) {
        Exceptionhandling exceptionhandling = new Exceptionhandling();
        String[] grades = {"70", "88", "$100", "93", "83"};

        CommonUtils.divSection("cannotCatch");
        //exceptionhandling.cannotCatch();

        CommonUtils.divSection("onErrorReturn");
        exceptionhandling.onErrorREturn(grades);

        CommonUtils.divSection("onError");
        exceptionhandling.onError(grades);

        CommonUtils.divSection("onErrorReturnItem");
        exceptionhandling.onErrorReturnItem(grades);

        CommonUtils.divSection("onErrorResumeNext");
        exceptionhandling.onErrorResumeNext();
    }

    private void cannotCatch(){
        Observable<String> observable = Observable.create(
                emitter -> {
                    emitter.onNext("1");
                    emitter.onError(new Exception("Some Error"));
                    emitter.onNext("3");
                    emitter.onComplete();
                }
        );

        try {
            observable.subscribe(Log::i);
        }catch (Exception e){
            Log.e(e.getMessage());
        }
    }

    private void onErrorREturn(String[] values){
        Observable<Integer> observable = Observable.fromArray(values)
                .map(data -> Integer.parseInt(data))
                .onErrorReturn(e -> {
                    if(e instanceof NumberFormatException){
                        e.printStackTrace();
                    }
                    return -1;
                });

        observable.subscribe(data -> {
            if(data < 0){
                Log.e("Wrong Data found!!");
                return;
            }
            Log.i("Grade is " + data);
            }
        );
    }

    private void onError(String[] values){
        Observable<Integer> observable = Observable.fromArray(values)
                .map(Integer::parseInt);

        observable.subscribe(data -> Log.i("Grade is " + data),
                e -> {
                        if(e instanceof NumberFormatException){
                            e.printStackTrace();
                        }
                        Log.e("Wrong Data found!!");
                    }
                );
    }

    private void onErrorReturnItem(String[] valuse){
        Observable<Integer> observable = Observable.fromArray(valuse)
                .map(data -> Integer.parseInt(data))
                .onErrorReturnItem(-1);

        observable.subscribe(data ->{
                if(data < 0){
                    Log.e("Wrong Data found");
                    return;
                }

                Log.i("Grade is " + data);
            }
        );
    }

    private void onErrorResumeNext(){
        String[] salesData = {"100", "200", "A300"};
        Observable<Integer> onParseError = Observable.defer(() ->{
            Log.d("send email to administrator");
            return Observable.just(-1);
            })
            .subscribeOn(Schedulers.io());

        Observable<Integer> observable = Observable.fromArray(salesData)
                .map(Integer::parseInt)
                .onErrorResumeNext(e -> onParseError);

        observable.subscribe(data -> {
                if(data < 0){
                    Log.e("Wrong Data found!!");
                    return;
                }

            Log.i("Sales data " + data);
            }
        );
    }
}
