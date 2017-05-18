package com.example.mvp_demo.view;

import com.example.mvp_demo.mode.beans.DailyThemes;

/**
 * Created by yangfan on 2017/4/24.
 */

public interface INavigationView extends IBaseView{
    void showThemes(DailyThemes themes);
}
