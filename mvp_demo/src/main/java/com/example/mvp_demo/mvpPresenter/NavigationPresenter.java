package com.example.mvp_demo.mvpPresenter;

import com.example.mvp_demo.mvpMode.beans.DailyThemes;
import com.example.mvp_demo.mvpMode.network.RetrofitService;
import com.example.mvp_demo.mvpView.INavigationView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by yangfan on 2017/4/24.
 */

public class NavigationPresenter implements IBasePresenter {

    private INavigationView mView;

    public NavigationPresenter(INavigationView view) {
        this.mView = view;
    }

    @Override
    public void requestData(final boolean isRefresh) {
        RetrofitService.getNewsService().getThemes()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!isRefresh) {
                            mView.showLoading();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyThemes>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(null);
                    }

                    @Override
                    public void onNext(DailyThemes themes) {
                        mView.showThemes(themes);
                    }
                });
    }

    @Override
    public void requestMoreData() {

    }
}
