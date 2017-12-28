package com.sean.lib;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by cssch on 2017/1/12.
 */

public class MultipartHttp {

    public static void main(String args[]) {

        OkHttpClient client = new OkHttpClient();
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpg"), new File("d:/first_code.png"));

        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("filename", "testtest.jpg", imageBody)
                .setType(MultipartBody.FORM)
                .addFormDataPart("name","sean")
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:8080/UploadServlet")
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
