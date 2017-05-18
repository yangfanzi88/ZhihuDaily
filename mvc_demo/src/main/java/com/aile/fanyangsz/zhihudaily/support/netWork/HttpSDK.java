package com.aile.fanyangsz.zhihudaily.support.netWork;

import android.text.TextUtils;
import android.util.Log;

import com.aile.fanyangsz.zhihudaily.support.beans.NewsBeans;
import com.aile.fanyangsz.zhihudaily.support.beans.NewsDetailBean;
import com.aile.fanyangsz.zhihudaily.support.beans.PictureBeans;
import com.aile.fanyangsz.zhihudaily.support.beans.WelcomeBean;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Category;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Feature;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Issue;
import com.aile.fanyangsz.zhihudaily.support.netWork.retrofitHttp.HttpUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by fanyang.sz on 2016/11/7.
 */

public class HttpSDK {

    private String TAG = HttpSDK.class.getSimpleName();
    private static HttpSDK mInstance;

    public static HttpSDK getInstance() {
        if (mInstance == null) mInstance = new HttpSDK();
        return mInstance;
    }

    private HttpSDK() {}

    public interface onWelcomeCallBack{
        void onSuccess(WelcomeBean data);

        void onError(String s);
    }

    public void getWelcome(String size, final onWelcomeCallBack callBack){
        HttpUtil.getNewsService().getWelComeImage(size).enqueue(new Callback<WelcomeBean>() {
            @Override
            public void onResponse(Call<WelcomeBean> call, Response<WelcomeBean> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<WelcomeBean> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }


    public interface onNewsCallBack {
        void onSuccess(NewsBeans data);

        void onError(String s);
    }

    public void getNews(String date, final onNewsCallBack callBack) {

        if (TextUtils.isEmpty(date)) {
            HttpUtil.getNewsService().getLatestNews().enqueue(new Callback<NewsBeans>() {
                @Override
                public void onResponse(Call<NewsBeans> call, Response<NewsBeans> response) {
                    Log.i(TAG, response.toString());
                    callBack.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<NewsBeans> call, Throwable t) {
                    Log.i(TAG, call.toString());
                    callBack.onError(t.getMessage());
                }
            });
        }else{
            HttpUtil.getNewsService().getBeforeNews(date).enqueue(new Callback<NewsBeans>() {
                @Override
                public void onResponse(Call<NewsBeans> call, Response<NewsBeans> response) {
                    callBack.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<NewsBeans> call, Throwable t) {
                    callBack.onError(t.getMessage());
                }
            });
        }
    }

    public interface onDetailCallBack{
        void onSuccess(NewsDetailBean bean);
        void onError(String s);
    }
    public void getNewsDetail(int id, final onDetailCallBack onDetailCallBack){
        HttpUtil.getNewsService().getNewsDetail(id).enqueue(new Callback<NewsDetailBean>() {
            @Override
            public void onResponse(Call<NewsDetailBean> call, Response<NewsDetailBean> response) {
                onDetailCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<NewsDetailBean> call, Throwable t) {
                onDetailCallBack.onError(t.getMessage());
            }
        });
    }


    public interface onPictureCallBack{
        void onSuccess(PictureBeans beans);
        void onError(String s);
    }
    public void getPicture(int page, final onPictureCallBack onPictureCallBack){
        HttpUtil.getPictureService().getBenefit(page).enqueue(new Callback<PictureBeans>() {
            @Override
            public void onResponse(Call<PictureBeans> call, Response<PictureBeans> response) {
                onPictureCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<PictureBeans> call, Throwable t) {
                onPictureCallBack.onError(t.getMessage());
            }
        });
    }


    public interface onVideoCategoryCallBack{
        void onSuccess(List<Category> categoryList);
        void onError(String s);
    }
    public void getVideoCategory(final onVideoCategoryCallBack categoryCallBack){
        HttpUtil.getVideoService().getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                categoryCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                categoryCallBack.onError(t.getMessage());
            }
        });
    }

    public interface onVideoTodayFeatureCallBack {
        void onSuccess(Feature beans);
        void onError(String s);
    }
    public void getVideoTodayFeature(final onVideoTodayFeatureCallBack callBack){
        HttpUtil.getVideoService().getTodayFeature().enqueue(new Callback<Feature>() {
            @Override
            public void onResponse(Call<Feature> call, Response<Feature> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Feature> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }

    public interface onVideoFeatureCallBack{
        void onSuccess(Issue issueBean, String categoryName);
        void onError(String s);
    }
    public void getVideoFeature(final String categoryName, final onVideoFeatureCallBack callBack){
        HttpUtil.getVideoService().getCategoryFeature(
                "date",
                "26868b32e808498db32fd51fb422d00175e179df",
                "83",
                categoryName
        ).enqueue(new Callback<Issue>() {
            @Override
            public void onResponse(Call<Issue> call, Response<Issue> response) {
                callBack.onSuccess(response.body(),categoryName);
            }

            @Override
            public void onFailure(Call<Issue> call, Throwable t) {
                callBack.onError(t.getMessage());
            }
        });
    }
}
