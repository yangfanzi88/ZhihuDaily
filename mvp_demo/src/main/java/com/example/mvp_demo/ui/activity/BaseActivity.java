package com.example.mvp_demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.mvp_demo.DailyApplication;
import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.ApplicationComponent;
import com.example.mvp_demo.injector.modules.ActivityModule;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.utils.SwipeRefreshHelper;
import com.example.mvp_demo.mvpView.IBaseView;
import com.example.mvp_demo.widget.EmptyLayout;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanyang.sz on 2016/11/10.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends RxAppCompatActivity implements IBaseView {
    /**
     * 把 EmptyLayout 放在基类统一处理，@Nullable 表明 View 可以为 null，详细可看 ButterKnife
     */
    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;
    /**
     * 刷新控件，注意，资源的ID一定要一样
     */
    @Nullable
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

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
        initViews(savedInstanceState);
        initSwipeRefresh();
        updateUI(false);
    }


    @Override
    public void showLoading() {
        if(mEmptyLayout != null){
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_LOADING);
        }
    }

    @Override
    public void hideLoading() {
        if(mEmptyLayout != null){
            mEmptyLayout.hide();
        }
    }

    @Override
    public void showError(EmptyLayout.OnRetryListener listener) {
        if (mEmptyLayout != null) {
            mEmptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            mEmptyLayout.setRetryListener(listener);
        }
    }

    @Override
    public void finishRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    private void initSwipeRefresh(){
        if(mSwipeRefresh != null){
            SwipeRefreshHelper.init(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateUI(true);
                }
            });
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }


    protected ApplicationComponent getAppComponent() {
        return DailyApplication.getAppComponent();
//        return ((AndroidApplication) getApplication()).getAppComponent();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void addFragment(int containerViewId, Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * 替换 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            getSupportFragmentManager().popBackStack(tag, 0);
        }
    }

    /**
     * 绑定布局
     * @return 布局文件ID
     */
    protected abstract int inflateContentView();

    /**
     * 初始化Views
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * Dragger 注入
     */
    protected abstract void initInjector();

    /**
     * 更新UI
     */
    protected abstract void updateUI(boolean isRefresh);
}
