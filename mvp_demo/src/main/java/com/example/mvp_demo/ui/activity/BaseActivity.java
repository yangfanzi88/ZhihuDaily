package com.example.mvp_demo.ui.activity;

import android.os.Bundle;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.injector.component.ApplicationComponent;
import com.example.mvp_demo.injector.modules.ActivityModule;
import com.example.mvp_demo.presenter.IBasePresenter;
import com.example.mvp_demo.view.IBaseView;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by fanyang.sz on 2016/11/10.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView {


    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true有小箭头，并且图标可以点击
//        actionBar.setDisplayShowHomeEnabled(true);// 使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id为android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME


        setContentView(inflateContentView());
        ButterKnife.bind(this);
        initInjector();
        initViews();
        updateUI(false);
    }

    /**
     * 绑定布局
     * @return 布局文件ID
     */
    protected abstract int inflateContentView();

    /**
     * 初始化Views
     */
    protected abstract void initViews();

    /**
     * Dragger 注入
     */
    protected abstract void initInjector();

    /**
     * 更新UI
     */
    protected abstract void updateUI(boolean isRefresh);


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return null;
    }


    protected ApplicationComponent getAppComponent() {
        return DailyApplication.getAppComponent();
//        return ((AndroidApplication) getApplication()).getAppComponent();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }
}
