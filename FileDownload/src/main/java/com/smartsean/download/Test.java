package com.smartsean.download;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author SmartSean
 * @date 17/12/7 15:32
 */
public class Test {
    public static void main(String[] args){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://www.imooc.com").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(request.body().toString());
            }
        });
    }
}
