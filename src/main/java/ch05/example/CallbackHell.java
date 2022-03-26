package ch05.example;

import common.CommonUtils;
import common.Log;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CallbackHell {
    private static final String FIRST_URL = "https://api.github.com/zen";
    private static final String SECOND_URL  = CommonUtils.GITHUB_ROOT + "samples/callback_hell";

    private final OkHttpClient client = new OkHttpClient();

    private Callback onSuccess = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            Log.i(response.body().string());
        }
    };

    public static void main(String[] args) {
        CallbackHell callbackHell = new CallbackHell();

        CommonUtils.divSection("run");
        callbackHell.run();
    }

    private void run(){
        Request request = new Request.Builder()
                .url(FIRST_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(response.body().string());

                Request request = new Request.Builder()
                        .url(SECOND_URL)
                        .build();

                client.newCall(request).enqueue(onSuccess);
            }
        });
    }
}
