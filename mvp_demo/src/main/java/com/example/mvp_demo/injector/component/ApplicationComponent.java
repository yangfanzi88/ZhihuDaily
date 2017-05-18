package com.example.mvp_demo.injector.component;

import android.content.Context;

import com.example.mvp_demo.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yangfan on 2017/4/5.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();
}
