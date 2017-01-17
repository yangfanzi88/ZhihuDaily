package com.aile.fanyangsz.zhihudaily.support.netWork.retrofitHttp;

import com.aile.fanyangsz.zhihudaily.support.beans.PictureBeans;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fanyang.sz on 2016/11/4.
 */
public interface PictureService {

    @GET("api/data/福利/20/{page}")
    Call<PictureBeans> getBenefit(@Path("page") int page);

}
