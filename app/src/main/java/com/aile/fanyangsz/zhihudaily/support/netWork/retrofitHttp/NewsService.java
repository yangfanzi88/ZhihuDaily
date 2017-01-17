package com.aile.fanyangsz.zhihudaily.support.netWork.retrofitHttp;

import com.aile.fanyangsz.zhihudaily.support.beans.NewsBeans;
import com.aile.fanyangsz.zhihudaily.support.beans.NewsDetailBean;
import com.aile.fanyangsz.zhihudaily.support.beans.WelcomeBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fanyang.sz on 2016/11/4.
 */
public interface NewsService {

    //http://news-at.zhihu.com/api/4/news/latest
    @GET("api/4/news/latest")
    Call<NewsBeans> getLatestNews();

    //http://news.at.zhihu.com/api/4/news/before/20160831
    //返回的是20160830的头条。url需延后一天。
    @GET("api/4/news/before/{date}")
    Call<NewsBeans> getBeforeNews(@Path("date") String date);

    //http://news-at.zhihu.com/api/4/news/8725424
    @GET("api/4/news/{id}")
    Call<NewsDetailBean> getNewsDetail(@Path("id") int id);

    //http://news-at.zhihu.com/api/4/start-image/{size}
    @GET("api/4/start-image/{size}")
    Call<WelcomeBean> getWelComeImage(@Path("size") String size);
}
