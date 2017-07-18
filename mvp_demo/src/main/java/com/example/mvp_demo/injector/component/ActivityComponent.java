package com.example.mvp_demo.injector.component;

import com.example.mvp_demo.injector.modules.ActivityModule;
import com.example.mvp_demo.injector.scope.PerActivity;

import dagger.Component;

/**
 * Created by yangfan on 2017/3/30.
 */

@PerActivity
@Component(modules = ActivityModule.class,dependencies = ApplicationComponent.class)
public interface ActivityComponent {
//    Activity getContext();
}
