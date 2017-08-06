package com.example.mvp_demo.injector.modules;

import com.example.mvp_demo.injector.scope.PerFragment;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpPresenter.ThemeStoryPresenter;
import com.example.mvp_demo.ui.adapter.ThemeStoryAdapter;
import com.example.mvp_demo.ui.fragment.ThemeStoriesFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangfan on 2017/7/23.
 */

@Module
public class ThemeStoryModule {
    private final ThemeStoriesFragment mThemeStoryView;
    private final int mThemeId;

    public ThemeStoryModule(ThemeStoriesFragment mThemeStoryView, int mThemeId) {
        this.mThemeStoryView = mThemeStoryView;
        this.mThemeId = mThemeId;
    }

    @PerFragment
    @Provides
    public IBasePresenter providerPresenter(){
        return new ThemeStoryPresenter(mThemeStoryView, mThemeId);
    }

    @PerFragment
    @Provides
    public ThemeStoryAdapter providerAdapter(){
        return new ThemeStoryAdapter();
    }
}
