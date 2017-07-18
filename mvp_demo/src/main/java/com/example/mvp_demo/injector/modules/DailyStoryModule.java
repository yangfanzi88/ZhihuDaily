package com.example.mvp_demo.injector.modules;

import android.support.v7.widget.RecyclerView;

import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.mvpPresenter.DailyStoryPresenter;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.ui.adapter.DailyStoryAdapter;
import com.example.mvp_demo.ui.fragment.DailyStoriesFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangfan on 2017/6/12.
 */

@Module
public class DailyStoryModule {

    private final DailyStoriesFragment mDailyStoryView;

    public DailyStoryModule(DailyStoriesFragment mDailyStoryView) {
        this.mDailyStoryView = mDailyStoryView;
    }

    @PerFragment
    @Provides
    public IBasePresenter providerPresenter(){
        return new DailyStoryPresenter(mDailyStoryView);
    }


    @PerFragment
    @Provides
    public DailyStoryAdapter providerAdapter(){
        return new DailyStoryAdapter();
    }
}
