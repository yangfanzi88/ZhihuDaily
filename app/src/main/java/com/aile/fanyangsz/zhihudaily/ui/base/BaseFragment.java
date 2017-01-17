package com.aile.fanyangsz.zhihudaily.ui.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment的基类
 * Created by fanyang.sz on 2016/11/8.
 */

public abstract class BaseFragment extends Fragment {
    private View view;
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(inflateContentView(), container, false);
        unbinder = ButterKnife.bind(this,view);
        initView();
        requestData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int inflateContentView();
    protected abstract void initView();
    protected abstract void requestData();
}
