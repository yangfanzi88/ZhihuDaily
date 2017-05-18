package com.example.mvp_demo;

import android.app.Application;
import android.content.Context;

import com.example.mvp_demo.injector.component.ApplicationComponent;
import com.example.mvp_demo.injector.component.DaggerApplicationComponent;
import com.example.mvp_demo.injector.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by yangfan on 2017/4/11.
 */

public class DailyApplication extends Application{
    private static ApplicationComponent sAppComponent;
    private static Context sContext;
    private static RefWatcher sRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        _initInjector();
        _initConfig();
    }

    private void _initInjector(){
        sAppComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void _initConfig(){
        sRefWatcher = LeakCanary.install(this);

    }

    public static Context getContext() {
        return sContext;
    }

    public static ApplicationComponent getAppComponent(){
        return sAppComponent;
    }
}
