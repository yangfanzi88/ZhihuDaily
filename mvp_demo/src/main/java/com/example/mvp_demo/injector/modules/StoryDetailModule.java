package com.example.mvp_demo.injector.modules;

import com.example.mvp_demo.injector.scope.PerActivity;
import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpPresenter.StoryDetailPresenter;
import com.example.mvp_demo.mvpView.IStoryDetailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangfan on 2017/8/27.
 */

@Module
public class StoryDetailModule {
    private IStoryDetailView mView;
    private int mStoryId;

    public StoryDetailModule(IStoryDetailView mView, int mStoryId){
        this.mView = mView;
        this.mStoryId = mStoryId;
    }

    @PerFragment
    @Provides
    public IBasePresenter providerPresenter(){
        return new StoryDetailPresenter(mView, mStoryId);
    }
}
