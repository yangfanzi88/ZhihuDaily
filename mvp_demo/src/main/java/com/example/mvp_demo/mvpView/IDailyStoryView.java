package com.example.mvp_demo.mvpView;

import com.example.mvp_demo.mvpMode.beans.DailyStories;

/**
 * Created by yangfan on 2017/6/12.
 */

public interface IDailyStoryView extends IBaseView{
    void showDailyStory(DailyStories stories);
}
