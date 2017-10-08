package com.example.mvp_demo.injector.modules;

import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpPresenter.StoryCommentPresenter;
import com.example.mvp_demo.mvpView.IStoryCommentView;
import com.example.mvp_demo.ui.fragment.StoryCommentFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangfan on 2017/10/8.
 */

@Module
public class StoryCommentModule {

    private IStoryCommentView mView;
    private int storyId;

    public StoryCommentModule(IStoryCommentView mView, int storyId) {
        this.mView = mView;
        this.storyId = storyId;
    }

    @PerFragment
    @Provides
    public IBasePresenter providerPresenter(){
        return new StoryCommentPresenter(mView, storyId);
    }
}
