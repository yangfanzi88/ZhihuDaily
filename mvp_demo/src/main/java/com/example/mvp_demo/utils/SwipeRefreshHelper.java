package com.example.mvp_demo.utils;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by yangfan on 2017/6/1.
 * 下拉刷新的帮助类
 */

public class SwipeRefreshHelper {

    /*************************下拉刷新的初始化**************／
    /**
     * 初始化，关联AppBarLayout，处理滑动冲突
     * 如果不对AppBarLayout设置addOnOffsetChangedListener会出现滑动的冲突
     * @param refreshLayout
     * @param appBar
     * @param listener
     */
    public static void init(final SwipeRefreshLayout refreshLayout, AppBarLayout appBar, SwipeRefreshLayout.OnRefreshListener listener){
        //下拉刷新动画的颜色设置
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(listener);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    refreshLayout.setEnabled(true);
                } else {
                    refreshLayout.setEnabled(false);
                }
            }
        });
    }

    /**
     * 对swipeRefreshLayout的初始化
     * @param refreshLayout
     * @param listener
     */
    public static void init(final SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener listener){
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(listener);
    }

    /**
     * 使能刷新
     * @param refreshLayout
     * @param isEnable
     */
    public static void enableRefresh(SwipeRefreshLayout refreshLayout, boolean isEnable) {
        if (refreshLayout != null) {
            refreshLayout.setEnabled(isEnable);
        }
    }

    /**
     * 控制刷新
     * @param refreshLayout
     * @param isRefresh
     */
    public static void controlRefresh(SwipeRefreshLayout refreshLayout, boolean isRefresh) {
        if (refreshLayout != null) {
            if (isRefresh != refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(isRefresh);
            }
        }
    }



}
