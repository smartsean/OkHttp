package com.css.okhttp3.example.retrofit_post;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.css.okhttp3.R;
import com.css.okhttp3.example.Retrofit.HttpMethods;
import com.css.okhttp3.example.Retrofit.progress.ProgressSubscriber;
import com.css.okhttp3.example.Retrofit.Subject;
import com.css.okhttp3.example.Retrofit.SubscriberOnNextListener;
import com.css.okhttp3.example.retrofit_post.api.ApiService;
import com.css.okhttp3.example.retrofit_post.bean.LoginParameters;
import com.css.okhttp3.example.retrofit_post.bean.LoginRequest;
import com.css.okhttp3.example.retrofit_post.bean.ResponseLoginBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.css.okhttp3.example.retrofit_post.config.ApiConstants.LOGIN_URL;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity----->";
    private SubscriberOnNextListener getTopMovieOnNext;

    @BindView(R.id.post_show)
    TextView postShow;
    @BindView(R.id.post0)
    Button post0;
    @BindView(R.id.post1)
    Button post1;
    @BindView(R.id.post2)
    Button post2;
    @BindView(R.id.post3)
    Button post3;
    @BindView(R.id.post4)
    Button post4;
    @BindView(R.id.post5)
    Button post5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        getTopMovieOnNext = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                if (o == null) {
                    Log.d(TAG, "onNext: " + "Object为空");
                    Log.d(TAG, "哈哈哈，成功了");
                } else {
                    Log.d(TAG, "onNext: " + o.toString());
                    postShow.setText(o.toString());
                }
            }
        };
    }


    @OnClick({R.id.post0, R.id.post1, R.id.post2, R.id.post3, R.id.post4, R.id.post5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post0:
                post0();
                break;
            case R.id.post1:
                post1();
                break;
            case R.id.post2:
                post2();
                break;
            case R.id.post3:
                post3();
                break;
            case R.id.post4:
                break;
            case R.id.post5:
                break;
        }
    }

    private void post3() {
        com.css.okhttp3.example.retrofit_post.HttpMethods.getInstance().login(new Subscriber<ResponseLoginBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(Main2Activity.this, "完成队列", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.toString());
                Log.d(TAG, "onError: " + e.getMessage());
                Toast.makeText(Main2Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseLoginBean responseLoginBeen) {
                Log.d(TAG, "onNext: " + responseLoginBeen.toString());
                Toast.makeText(Main2Activity.this, "完成队列", Toast.LENGTH_SHORT).show();
                postShow.setText(responseLoginBeen.getMemberCode());
            }
        }, getRequestBody());
    }

    private void post2() {
        HttpMethods.getInstance().getTopMoviesUseMap(new ProgressSubscriber<List<Subject>>(new SubscriberOnNextListener<List<Subject>>() {

            @Override
            public void onNext(List<Subject> subjects) {
                postShow.setText(subjects.get(0).getId() + "---title---:" + subjects.get(0).getTitle());
            }
        }, Main2Activity.this), 0, 10);
    }

    private void post1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.i5sesol.com/cgi/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        LoginRequest loginRequest = getRequestBody();

        Observable<ResponseBody> observable = apiService.loginRxJava(loginRequest);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(Main2Activity.this, "准备完毕", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Log.d(TAG, "onNext: " + responseBody.string());
                            Log.d(TAG, "onNext: " + responseBody.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private LoginRequest getRequestBody() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCmd(LOGIN_URL);
        LoginParameters loginParameters = new LoginParameters();
        loginParameters.setMemberName("18521069753");
        loginParameters.setPassword("123456");
        loginRequest.setParameters(loginParameters);
        return loginRequest;
    }

    private void post0() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.i5sesol.com/cgi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        LoginRequest loginRequest = getRequestBody();


        Log.d(TAG, "onClick: " + loginRequest);

        Call<ResponseBody> call = apiService.login(loginRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }
}
