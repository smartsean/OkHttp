package com.css.okhttp3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.css.okhttp3.example.OkHttp;
import com.css.okhttp3.example.Retrofit.RetrofitActivity;
import com.css.okhttp3.example.retrofit_post.Main2Activity;
import com.github.zagum.switchicon.SwitchIconView;
import com.smartsean.download.DownloadManager;
import com.smartsean.download.http.DownloadCallback;
import com.smartsean.download.http.HttpManager;
import com.smartsean.download.utils.Logger;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.ok_http)
    Button okHttp;
    @BindView(R.id.retrofit)
    Button retrofit;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.test)
    SwitchIconView test;
    @BindView(R.id.image)
    ImageView imageView;

//    private String url = "https://gold-cdn.xitu.io/v3/static/img/android.fef4da1.png";
    private String url = "http://coding.imooc.com/static/module/class/content/img/165/2.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        test.setIconEnabled(true);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test.isIconEnabled()) {
                    test.setIconEnabled(false);
                } else {
                    test.setIconEnabled(true);
                }
            }
        });


        DownloadManager.getInstance().download(url, new DownloadCallback() {
            @Override
            public void success(final File file) {
                Logger.d("smartsean","下载成功");

                Logger.d("smartsean", file.getAbsolutePath());
                Logger.d("smartsean", file.getName());
                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }

            @Override
            public void fail(int errorCode, String errorMessage) {
                Logger.d("smartsean","下载失败");

                Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void progress(int progress) {

            }
        });
//
//
//        HttpManager.getInstance().asyncRequest(url, new DownloadCallback() {
//            @Override
//            public void success(final File file) {
//                Logger.d("smartsean", file.getAbsolutePath());
//                Logger.d("smartsean", file.getName());
//                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
//                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                });
//            }
//
//            @Override
//            public void fail(int errorCode, String errorMessage) {
//                Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void progress(int progress) {
//
//            }
//        });
    }


    @OnClick({R.id.ok_http, R.id.retrofit, R.id.retrofit_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_http:
                startActivity(new Intent(this, OkHttp.class));
                break;
            case R.id.retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.retrofit_post:
                startActivity(new Intent(this, Main2Activity.class));
                break;
        }
    }
}
