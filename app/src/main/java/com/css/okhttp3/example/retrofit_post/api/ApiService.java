package com.css.okhttp3.example.retrofit_post.api;

import com.css.okhttp3.example.Retrofit.HttpResult;
import com.css.okhttp3.example.Retrofit.Subject;
import com.css.okhttp3.example.retrofit_post.bean.HttpResultGank;
import com.css.okhttp3.example.retrofit_post.bean.LoginRequest;
import com.css.okhttp3.example.retrofit_post.bean.ResponseLoginBean;
import com.css.okhttp3.example.retrofit_post.bean.ResultBean;
import com.css.okhttp3.example.retrofit_post.bean.Results;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by css on 17/3/2.
 */

public interface ApiService {
    @POST("./")
    Call<ResponseBody> login(@Body LoginRequest loginRequest);

    @POST("./")
    Observable<ResponseBody> loginRxJava(@Body LoginRequest loginRequest);

    @POST("./")
    Observable<ResultBean<ResponseLoginBean>> loginRxJavaHttpMethod(@Body LoginRequest loginRequest);

    @GET("/{count}/{page}")
    Observable<HttpResultGank<List<Results>>> getWelfare(@Path("count") int count, @Path("page") int page);
}
