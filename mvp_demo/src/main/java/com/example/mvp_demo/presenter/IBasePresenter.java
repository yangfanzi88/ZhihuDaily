package com.example.mvp_demo.presenter;

/**
 * Created by yangfan on 2017/4/6.
 * 基础Presenter
 */

public interface IBasePresenter {

    /**
     * 获取数据
     * @param isRefresh 用来判断是否为下拉刷新调用，下拉刷新的时候不应该再显示加载界面和异常界面
     */
    void requestData(boolean isRefresh);

}
