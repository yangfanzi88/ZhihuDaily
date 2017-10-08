package com.example.mvp_demo.ui.fragment;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.injector.component.DaggerStoryCommentComponent;
import com.example.mvp_demo.injector.modules.StoryCommentModule;
import com.example.mvp_demo.mvpMode.beans.StoryComment;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpView.IStoryCommentView;

/**
 * Created by yangfan on 2017/10/3.
 */

public class StoryCommentFragment extends BaseFragment<IBasePresenter> implements IStoryCommentView {

    @Override
    protected int inflateContentView() {
        return 0;
    }

    @Override
    protected void initInjector() {
        DaggerStoryCommentComponent.builder()
                .applicationComponent(DailyApplication.getAppComponent())
                .storyCommentModule(new StoryCommentModule(this,0))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews(boolean isRefresh) {

    }

    @Override
    public void showComment(StoryComment storyComment) {

    }
}
