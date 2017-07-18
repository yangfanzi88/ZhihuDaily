package com.example.mvp_demo.mvpMode.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangfan on 2017/3/29.
 */

public class RetrofitService {
    public static final String ZHIHU_URL = "http://news-at.zhihu.com/api/4/";

    private static ApiService mZhifuService;

    //NewsService：单例模式。
    public static synchronized ApiService getNewsService() {
        if (mZhifuService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ZHIHU_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mZhifuService = retrofit.create(ApiService.class);
        }
        return mZhifuService;
    }
}
