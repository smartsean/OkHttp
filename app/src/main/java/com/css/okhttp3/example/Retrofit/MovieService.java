package com.css.okhttp3.example.Retrofit;


import com.css.okhttp3.example.retrofit_post.bean.HttpResultGank;
import com.css.okhttp3.example.retrofit_post.bean.Results;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 用来结合Retrofit请求数据的接口
 * Created by sean on 2017/3/1.
 */

public interface MovieService {

    /**
     * 使用最简单的原始的办法请求数据 Retrofit
     *
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    /**
     * 结合RxJava请求数据 Retrofit
     *
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Observable<MovieEntity> getTopMovieRxJava(@Query("start") int start, @Query("count") int count);

    /**
     * 使用泛型来和RxJava中的map()方法来请求数据
     *
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovieResultRxJava(@Query("start") int start, @Query("count") int count);


    @GET("{count}/{page}")
    Observable<HttpResultGank<List<Results>>> getWelfare(@Path("count") int count, @Path("page") int page);
}
