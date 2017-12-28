package com.css.okhttp3.example.Retrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.css.okhttp3.R;
import com.css.okhttp3.example.Retrofit.progress.ProgressSubscriber;
import com.css.okhttp3.example.retrofit_post.bean.Results;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

import static com.css.okhttp3.example.Retrofit.HttpMethods.BASE_URL;

public class RetrofitActivity extends AppCompatActivity {

    private static final String TAG = "Retrofit";

    private static final String URL = "";
    @BindView(R.id.image)
    ImageView image;

    private SubscriberOnNextListener getTopMovieOnNext;
    private SubscriberOnNextListener getTopWelFareOnNext;

    private Subscriber subscriber;

    @BindView(R.id.retrofit_show)
    TextView retrofitShow;
    @BindView(R.id.retrofit_btn0)
    Button retrofitBtn0;
    @BindView(R.id.retrofit_btn1)
    Button retrofitBtn1;
    @BindView(R.id.retrofit_btn2)
    Button retrofitBtn2;
    @BindView(R.id.retrofit_btn3)
    Button retrofitBtn3;
    @BindView(R.id.retrofit_btn4)
    Button retrofitBtn4;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        context = RetrofitActivity.this;
        getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                retrofitShow.setText(subjects.get(0).getTitle());
            }
        };
    }

    @OnClick({R.id.retrofit_btn0, R.id.retrofit_btn1, R.id.retrofit_btn2, R.id.retrofit_btn3,
            R.id.retrofit_btn4, R.id.retrofit_btn5, R.id.retrofit_btn6, R.id.retrofit_btn7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retrofit_btn0:
                getMovie();
                break;
            case R.id.retrofit_btn1:
                getMovieRxJava();
                break;
            case R.id.retrofit_btn2:
                getTopMovie();
                break;
            case R.id.retrofit_btn3:
                getResultCodeHandleTopMovie();
                break;
            case R.id.retrofit_btn4:
                if (!subscriber.isUnsubscribed()) {
                    subscriber.unsubscribe();
                }
                break;
            case R.id.retrofit_btn5:
                HttpMethods.getInstance().getTopMoviesUseMap(new ProgressSubscriber(getTopMovieOnNext, context), 0, 10);
                break;
            case R.id.retrofit_btn6:
                HttpMethods.getInstance().getTopMoviesUseMap(new ProgressSubscriber(getTopMovieOnNext, context), 0, 10);
                break;
            case R.id.retrofit_btn7:
                HttpMethods.getInstance().getWelfare(new ProgressSubscriber<List<Results>>(new SubscriberOnNextListener<List<Results>>() {
                    @Override
                    public void onNext(List<Results> resultses) {
                        image.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onNext: " + resultses.get(0).getUrl().toString());
                        Glide.with(context).load(resultses.get(0).getUrl().toString())
//                                .animate(R.anim.zoom_in)
                                .crossFade()
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
//                                .transform(new GlideCircleTransform(context))//转成圆形图片
                                .into(image);
                    }
                }, context), 10, 1);
                break;
        }
    }

    /**
     * 处理过的返回请求
     */
    private void getResultCodeHandleTopMovie() {
        subscriber = new Subscriber<List<Subject>>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RetrofitActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RetrofitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onNext(List<Subject> subjects) {
                retrofitShow.setText(subjects.get(0).getTitle() + "Map转换封装");
            }
        };
        HttpMethods.getInstance().getTopMoviesUseMap(subscriber, 0, 10);
    }

    /**
     * 使用封装的方法获取数据
     */
    private void getTopMovie() {
        HttpMethods.getInstance().getTopMovie(new Subscriber<MovieEntity>() {
            @Override
            public void onCompleted() {
                Toast.makeText(RetrofitActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MovieEntity movieEntity) {
                retrofitShow.setText(movieEntity.getTitle() + "封装" + movieEntity.getCount());
            }
        }, 0, 10);
    }

    //进行网络请求
    private void getMovie() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        Call<MovieEntity> call = movieService.getTopMovie(0, 10);

        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                retrofitShow.setText(response.body().getTitle() + response.body().getCount());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                retrofitShow.setText(t.getMessage());
            }
        });
    }

    //进行网络请求
    private void getMovieRxJava() {
        String baseUrl = BASE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);

        Observable<MovieEntity> observable = movieService.getTopMovieRxJava(0, 10);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieEntity>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(RetrofitActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        retrofitShow.setText(movieEntity.getTitle() + "个数" + movieEntity.getCount());
                    }
                });


        /**
         * 使用最原始的Retrofit请求
         */
//        call.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                retrofitShow.setText(response.body().getTitle() + response.body().getCount());
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                retrofitShow.setText(t.getMessage());
//            }
//        });
    }


    @OnClick(R.id.image)
    public void onClick() {
    }
}
