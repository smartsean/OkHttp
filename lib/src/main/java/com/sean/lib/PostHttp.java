package com.sean.lib;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sean on 2017/3/3.
 */

public class PostHttp {
    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();

        /**
         * 构造上传参数
         */
        RequestBody body = new FormBody.Builder()

                .add("username", "css")
                .add("userage", "99")
                .build();

        /**
         * 构造请求
         */
        Request request = new Request.Builder()
                .url("http://localhost:8080/HelloServlet")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
