package com.sean.lib;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cssch on 2017/1/12.
 */

public class AsyncHttp {

    /**
     * 同步请求
     * created at 2017/1/12 15:46
     */
    public static void sendRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
//                System.out.print(response.body().string() +"同步线程");
                System.out.println(Thread.currentThread().getId()+"同步线程");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步请求
     * created at 2017/1/12 15:46
     */
    public static void sendAsyncRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        System.out.println(Thread.currentThread().getId());
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
//                    System.out.println(Thread.currentThread().getId() + "异步线程");
                    System.out.println(Thread.currentThread().getId() + "异步线程");
                }
            }
        });
    }

    public static void main(String args[]) {
        /**
         * 同步请求
         */
//        sendRequest("http://www.imooc.com");

        /**
         * 异步请求
         */
        sendAsyncRequest("http://www.imooc.com");
    }
}
