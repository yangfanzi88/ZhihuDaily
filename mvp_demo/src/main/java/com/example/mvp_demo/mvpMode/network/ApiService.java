package com.example.mvp_demo.mvpMode.network;

import com.example.mvp_demo.mvpMode.beans.DailyStories;
import com.example.mvp_demo.mvpMode.beans.DailyThemeStories;
import com.example.mvp_demo.mvpMode.beans.DailyThemes;
import com.example.mvp_demo.mvpMode.beans.StartImage;
import com.example.mvp_demo.mvpMode.beans.StoryComment;
import com.example.mvp_demo.mvpMode.beans.StoryDetail;
import com.example.mvp_demo.mvpMode.beans.StoryExtra;
import com.example.mvp_demo.mvpMode.beans.Version;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yangfan on 2017/3/26.
 */

public interface ApiService {

    //http://news-at.zhihu.com/api/4/start-image/1080*1776
    @GET("start-image/{width}*{height}")
    Observable<StartImage> getStartImage(@Path("width") int width, @Path("height") int height);

    //http://news-at.zhihu.com/api/4/version/android/2.3.0
    @GET("version/android/{version}")
    Observable<Version> getLatest(@Path("version") String version);

    //http://news-at.zhihu.com/api/4/news/latest
    @GET("news/latest")
    Observable<DailyStories> getLatestDailyStories();

    //http://news-at.zhihu.com/api/4/news/3892357
    @GET("news/{id}")
    Observable<StoryDetail> getStoryDetail(@Path("id") int id);

    //http://news-at.zhihu.com/api/4/news/before/20131119
    @GET("news/before/{date}")
    Observable<DailyStories> getBeforeDailyStories(@Path("date") String date);

    //http://news-at.zhihu.com/api/4/story-extra/{id}
    @GET("story-extra/{id}")
    Observable<StoryExtra> getStoryExtra(@Path("id") int id);

    //http://news-at.zhihu.com/api/4/story/8997528/long-comments
    @GET("story/{id}/long-comments")
    Observable<StoryComment> getStoryLongComments(@Path("id") int id);

    //http://news-at.zhihu.com/api/4/story/9652686/long-comments/before/30511242
    @GET("story/{storyId}/long-comments/before/{commentId}")
    Observable<StoryComment> getStoryBeforeLongComments(@Path("storyId") int storyId, @Path("commentId") int commentId);

    //http://news-at.zhihu.com/api/4/story/4232852/short-comments
    @GET("story/{id}/short-comments")
    Observable<StoryComment> getStoryShortComments(@Path("id") int id);

    //http://news-at.zhihu.com/api/4/story/9651534/short-comments/before/30494087
    @GET("story/{storyId}/short-comments/before/{commentId}")
    Observable<StoryComment> getStoryBeforeShortComments(@Path("storyId") int storyId, @Path("commentId") int commentId);

    //http://news-at.zhihu.com/api/4/themes
    @GET("themes")
    Observable<DailyThemes> getThemes();

    //http://news-at.zhihu.com/api/4/theme/11
    @GET("theme/{id}")
    Observable<DailyThemeStories> getThemeStories(@Path("id") int id);

    //http://news-at.zhihu.com/api/4/theme/{themeId}/before/{storyId}
    @GET("theme/{themeId}/before/{storyId}")
    Observable<DailyThemeStories> getThemeBeforeStory(@Path("themeId") String themeId, @Path("storyId") String storyId);

}
