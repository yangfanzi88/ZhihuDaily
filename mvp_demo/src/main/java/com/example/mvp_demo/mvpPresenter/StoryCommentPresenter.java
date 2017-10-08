package com.example.mvp_demo.mvpPresenter;

import com.example.mvp_demo.mvpView.IStoryCommentView;

/**
 * Created by yangfan on 2017/10/8.
 */

public class StoryCommentPresenter implements IBasePresenter {

    private IStoryCommentView mView;
    private int storyId;

    public StoryCommentPresenter(IStoryCommentView mView, int storyId) {
        this.mView = mView;
        this.storyId = storyId;
    }

    @Override
    public void requestData(boolean isRefresh) {

    }

    @Override
    public void requestMoreData() {

    }
}
