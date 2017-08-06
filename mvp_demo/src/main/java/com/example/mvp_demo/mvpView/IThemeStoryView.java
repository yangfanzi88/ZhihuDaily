package com.example.mvp_demo.mvpView;

import com.example.mvp_demo.mvpMode.beans.DailyThemeStories;

/**
 * Created by yangfan on 2017/7/23.
 */

public interface IThemeStoryView extends IBaseView {
    void showThemeStory(DailyThemeStories stories);
}
