package com.example.mvp_demo.mvpView;

import com.example.mvp_demo.mvpMode.beans.StoryDetail;
import com.example.mvp_demo.mvpMode.beans.StoryExtra;

/**
 * Created by yangfan on 2017/8/27.
 */

public interface IStoryDetailView extends IBaseView {
    void showDetail(StoryDetail detail);
    void showExtra(StoryExtra extra);
}
