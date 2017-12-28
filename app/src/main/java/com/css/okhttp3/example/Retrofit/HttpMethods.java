package com.css.okhttp3.example.Retrofit;

import com.css.okhttp3.example.retrofit_post.bean.HttpResultGank;
import com.css.okhttp3.example.retrofit_post.bean.Results;

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

/**
 * Created by sean on 2017/3/1.
 */

public class HttpMethods {

    /**
     * 定义全局的url
     */
//    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    public static final String BASE_URL = "http://gank.io/api/data/福利/";

    /**
     * 定义超时时间
     */
    private static final int DEFAULT_TIMEOUT = 5;

    /**
     * 对于一个请求地址，实例化一个唯一的Retrofit对象来进行网络请求
     */
    private Retrofit retrofit;

    /**
     * 定以一个请求所用到的接口
     */
    private MovieService movieService;

    /**
     * 构造方法私有，不让外接访问到，通过内部的方法获取
     */
    private HttpMethods() {
        //手动创建一个OKHttpClient并设置超时时间
        OkHttpClient.Builder httpClienBuilder = new OkHttpClient.Builder();
        /**
         * 设置连接超时时间
         */
        httpClienBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        /**
         * 创建设置了连接时间的retrofit
         */
        retrofit = new Retrofit.Builder()
                .client(httpClienBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(MovieService.class);
    }

    /**
     * 在访问httpMethods时创建单例，私有的，外面访问不到
     */
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    /**
     * 获取单例。外部通过调用该方法获取单利
     */
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 拥有获取豆瓣top250的数据，没有统一处理服务器返回的数据
     *
     * @param subscriber 由调用者传递过来的观察者对象
     * @param start      起始位置
     * @param count      获取长度
     */
    public void getTopMovie(Subscriber<MovieEntity> subscriber, int start, int count) {
        movieService.getTopMovieRxJava(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 用来统一处理Http的resultCode，并将HttpResult的Data，在这个例子里面就是subjects里面的数据。
     * 然后返回给Subscriber
     * 此处的ApiException是自己定义的一个异常处理类，便于处理服务返回的各种errorCode
     *
     * @param <T>Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCount() == 5) {
                throw new ApiException(100);
            }
            return httpResult.getSubjects();
        }
    }

    private class HttpResultFunc1<T> implements Func1<HttpResultGank<T>, T> {
        @Override
        public T call(HttpResultGank<T> tHttpResultGank) {
            if (tHttpResultGank.isError()) {
                throw new ApiException(111);
            }
            return tHttpResultGank.getResults();
        }
    }

    /**
     * 对返回结果先进行处理之后再返回，即转换
     */
    public void getTopMoviesUseMap(Subscriber<List<Subject>> subscriber, int start, int count) {
        movieService.getTopMovieResultRxJava(start, count)
                .map(new HttpResultFunc<List<Subject>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getWelfare(Subscriber<List<Results>> subscriber, int count, int page) {
        movieService.getWelfare(count, page)
                .map(new HttpResultFunc1<List<Results>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
