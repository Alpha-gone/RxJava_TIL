package ch05.example;

import common.CommonUtils;
import common.Log;
import common.OkHttpHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenWeatherMapV2 {
        private static final String URL =
                "https://api.openweathermap.org/data/2.5/weather?q=London&APPID=";

        public static void main(String[] args) {
            OpenWeatherMapV2 openWeatherMapV2 = new OpenWeatherMapV2();

            CommonUtils.divSection("run");
            openWeatherMapV2.run();
        }

        private void run(){
            Observable<String> observable = Observable.just(URL + "578a6391d379a8db705719b831a2c3f0")
                    .map(OkHttpHelper::getWithLog)
                    .subscribeOn(Schedulers.io())
                    .share()
                    .observeOn(Schedulers.newThread());

            observable.map(this::parseTemperature).subscribe(Log::it);
            observable.map(this::parseCityName).subscribe(Log::it);
            observable.map(this::parseCountry).subscribe(Log::it);

            CommonUtils.sleep(3000);
        }

        private String parseTemperature(String json){
            return parse(json, "\"temp\":[0-9]*.[0-9]*");
        }

        private String parseCityName(String json){
            return parse(json, "\"name\":\"[a-zA-Z]*\"");
        }

        private String parseCountry(String json){
            return parse(json, "\"country\":\"[a-zA-Z]*\"");
        }

        private String parse(String json, String regex){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(json);
            if(matcher.find()){
                return matcher.group();
            }

            return "N/A";
        }
}

