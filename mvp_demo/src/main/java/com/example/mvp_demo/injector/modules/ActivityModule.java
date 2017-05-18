package com.example.mvp_demo.injector.modules;

import android.app.Activity;
import android.content.Context;

import com.example.mvp_demo.injector.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yangfan on 2017/4/4.
 */

@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @PerActivity
    @Provides
    public Context getContext(){
        return mActivity;
    }
}
