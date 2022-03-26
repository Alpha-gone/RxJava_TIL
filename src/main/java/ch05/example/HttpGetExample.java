package ch05.example;

import common.CommonUtils;
import common.Log;
import okhttp3.*;
import okhttp3.internal.http2.Http2;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.http.HttpClient;

public class HttpGetExample {
    private static final String URL_README = CommonUtils.GITHUB_ROOT + "README.md";

    private final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        HttpGetExample httpGetExample = new HttpGetExample();

        CommonUtils.divSection("run");
        httpGetExample.run();
    }

    private void run(){
        Request request = new Request.Builder()
                .url(URL_README)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(response.body().string());
            }
        });
    }
}
