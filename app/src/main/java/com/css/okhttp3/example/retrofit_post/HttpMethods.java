package com.css.okhttp3.example.retrofit_post;

import android.util.Log;

import com.css.okhttp3.example.Retrofit.Subject;
import com.css.okhttp3.example.retrofit_post.api.ApiService;
import com.css.okhttp3.example.retrofit_post.bean.LoginRequest;
import com.css.okhttp3.example.retrofit_post.bean.ResponseLoginBean;
import com.css.okhttp3.example.retrofit_post.bean.ResultBean;
import com.css.okhttp3.example.retrofit_post.exception.ApiTestException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.css.okhttp3.example.retrofit_post.config.ApiConstants.BASE_URL;
import static com.css.okhttp3.example.retrofit_post.config.NetWorkConfig.DEFAULT_TIMEOUT;

/**
 * Created by sean on 2017/3/3.
 */

public class HttpMethods {

    private Retrofit retrofit;

    private ApiService apiService;

    private HttpMethods() {
        //手动设置OkHttpClient可以自定义
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 登录接口
     *
     * @param subscriber
     * @param loginRequest
     */
    public void login(Subscriber<ResponseLoginBean> subscriber, LoginRequest loginRequest) {
        apiService.loginRxJavaHttpMethod(loginRequest)
                .map(new HttpResultFunc<ResponseLoginBean>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 统一处理错误
     *
     * @param <T>
     */
    private class HttpResultFunc<T> implements Func1<ResultBean<T>, T> {

        @Override
        public T call(ResultBean<T> tResultBean) {
            if (tResultBean.getError() != null) {
                throw new ApiTestException(tResultBean.getError().getErrorInfo());
            }
            Log.d("HttpResultFunc", "call: "+"哈哈哈");
            return tResultBean.getResponse();
        }
    }
}
