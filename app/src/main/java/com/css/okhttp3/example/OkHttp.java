package com.css.okhttp3.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;


import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mingle.widget.LoadingView;
import com.mingle.widget.ShapeLoadingDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.css.okhttp3.R;

public class OkHttp extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String URL = "https://api.i5sesol.com/cgi";
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private Context context;

    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.show)
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn0:
                getRequest();
                break;
            case R.id.btn1:
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getPost();
//                    }
//                }, 5000);
                getPost();
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
        }
    }

    private void getPost() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Map<String, String> map = new HashMap<>();
        map.put("memberName", "18521069753");
        map.put("password", "123456");
        Map<String, Object> upMap = new HashMap<>();
        upMap.put("cmd", "cloudfactory/app/member/login");
        upMap.put("parameters", map);

        String jsonString = JSON.toJSONString(upMap);
        Log.d(TAG, "getPost: " + jsonString);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonString);

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resString = response.body().string();
                    JSONObject resObject = JSON.parseObject(resString);
                    Log.d(TAG, "onResponse: " + resString);
                    Log.d(TAG, "onResponse: " + resObject);
                    Log.d(TAG, "onResponse: " + resObject.getJSONObject("error"));
                    Log.d(TAG, "onResponse: " + resObject.getString("error"));
                    if (resObject.getJSONObject("error") == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OkHttp.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }


    /**
     * 发送get请求
     */
    private void getRequest() {
        final Request request = new Request.Builder()
                .get()
                .url("https://api.douban.com/v2/movie/top250?start=0&count=10").build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        show.setText(res);
                    }
                });
                Log.d(TAG, "onResponse: " + res);
            }
        });
    }
}
