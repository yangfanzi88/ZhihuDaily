package com.aile.fanyangsz.zhihudaily.support.netWork.retrofitHttp;


import com.aile.fanyangsz.zhihudaily.support.beans.video.Category;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Feature;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Issue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by fanyang.sz on 2016/11/4.
 */
public interface VideoService {

    //num为1返回一天的精选，为2返回两天的精选。
    @GET("api/v2/feed?num=1&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Call<Feature> getTodayFeature();

    @GET("api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    Call<List<Category>> getCategories();


    @GET("api/v3/videos")
    Call<Issue> getCategoryFeature(
            @Query("strategy") String strategy1,
            @Query("udid") String udid1,
            @Query("vc") String vc1,
            @Query("categoryName") String categoryName1
    );

}
