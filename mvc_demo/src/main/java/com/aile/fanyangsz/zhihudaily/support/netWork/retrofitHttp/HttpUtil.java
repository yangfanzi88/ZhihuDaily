package com.aile.fanyangsz.zhihudaily.support.netWork.retrofitHttp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fanyang.sz on 2016/11/4.
 */

public class HttpUtil {
    public static final String ZHIHU_URL = "http://news-at.zhihu.com/";
    public static final String GANK_URL = "http://gank.io/";
    public static final String EYEPETIZER_URL = "http://baobab.wandoujia.com/";

    private static NewsService mZhifuService;
    private static PictureService gankService;
    private static VideoService eyepetizerService;

    //NewsService：单例模式。
    public static synchronized NewsService getNewsService() {
        if (mZhifuService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ZHIHU_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mZhifuService = retrofit.create(NewsService.class);
        }
        return mZhifuService;
    }

    public static synchronized PictureService getPictureService() {
        if (gankService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GANK_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            gankService = retrofit.create(PictureService.class);
        }
        return gankService;
    }

    public static synchronized VideoService getVideoService() {
        if (eyepetizerService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(EYEPETIZER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            eyepetizerService = retrofit.create(VideoService.class);
        }
        return eyepetizerService;
    }

}
