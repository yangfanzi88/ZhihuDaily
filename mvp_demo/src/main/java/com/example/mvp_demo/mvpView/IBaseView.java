package com.example.mvp_demo.mvpView;

import com.example.mvp_demo.widget.EmptyLayout;
import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by yangfan on 2017/4/6.
 * BaseView的基础接口
 */

public interface IBaseView {

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载动画
     */
    void hideLoading();

    /**
     * 显示错误信息
     */
    void showError(EmptyLayout.OnRetryListener listener);

    /**
     * 绑定生命周期
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();


    /**
     * 完成刷新
     */
    void finishRefresh();
}
