package com.sean.lib;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cssch on 2017/1/12.
 */

public class QueryHttp {
    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = HttpUrl.parse("http://guolin.tech/api/weather")
                .newBuilder()
                .addQueryParameter("cityid", "CN101020600")
                .addQueryParameter("key", "d8e6926e433246e7b5c661174e4ca259")
                .build();
        System.out.println(httpUrl.toString());
        String url = httpUrl.toString();
        Request request = new Request.Builder().url(url).build();
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
