package com.example.mvp_demo.mvpPresenter;

import com.example.mvp_demo.mvpMode.beans.DailyThemeStories;
import com.example.mvp_demo.mvpMode.network.RetrofitService;
import com.example.mvp_demo.mvpView.IThemeStoryView;
import com.example.mvp_demo.utils.Logger;
import com.example.mvp_demo.widget.EmptyLayout;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


/**
 * Created by yangfan on 2017/7/23.
 */

public class ThemeStoryPresenter implements IBasePresenter {
    private static String TAG = ThemeStoryPresenter.class.getSimpleName();

    private IThemeStoryView mView;
    private int mThemeId, mThemeStoryId;

    public ThemeStoryPresenter(IThemeStoryView mView, int mThemeId) {
        this.mView = mView;
        this.mThemeId = mThemeId;
    }

    @Override
    public void requestData(final boolean isRefresh) {
        RetrofitService.getNewsService()
                .getThemeStories(mThemeId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(
                        new Action0() {
                            @Override
                            public void call() {
                                if(!isRefresh){
                                    mView.showLoading();
                                }
                            }
                        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyThemeStories>() {
                    @Override
                    public void onCompleted() {
                        if (isRefresh) {
                            mView.finishRefresh();
                        } else {
                            mView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG,e.toString() + " " + isRefresh);

                        if (isRefresh) {
                            mView.finishRefresh();
                            // 可以提示对应的信息，但不更新界面
                        } else {
                            mView.showError(new EmptyLayout.OnRetryListener() {
                                @Override
                                public void onRetry() {
                                    requestData(false);
                                }
                            });
                        }
                    }

                    @Override
                    public void onNext(DailyThemeStories dailyThemeStories) {
                        if(dailyThemeStories != null){
                            mView.showThemeStory(dailyThemeStories);
                            mThemeStoryId = dailyThemeStories.getStories().get(dailyThemeStories.getStories().size()-1).getId();
                        }
                    }
                });
    }

    @Override
    public void requestMoreData() {
        RetrofitService.getNewsService()
                .getThemeBeforeStory(String.valueOf(mThemeId), String.valueOf(mThemeStoryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyThemeStories>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DailyThemeStories dailyThemeStories) {
                        mView.showThemeStory(dailyThemeStories);
                        mThemeStoryId = dailyThemeStories.getStories().get(dailyThemeStories.getStories().size()-1).getId();
                    }
                });
    }
}
