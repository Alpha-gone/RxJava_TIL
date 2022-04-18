import io.reactivex.rxjava3.core.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Star {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer lineNum = 0;


        lineNum = Integer.parseInt(br.readLine());


        Observable.just(lineNum)
                .flatMap(num -> Observable.range(1, num)
                                            .map(line -> " ".repeat(num - line) + "*".repeat(2* line -1 )))
                .subscribe(System.out::println);

        br.close();

    }
}
