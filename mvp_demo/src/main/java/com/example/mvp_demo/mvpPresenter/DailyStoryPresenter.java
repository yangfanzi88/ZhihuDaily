package com.example.mvp_demo.mvpPresenter;

import android.nfc.Tag;

import com.example.mvp_demo.mvpMode.beans.DailyStories;
import com.example.mvp_demo.mvpMode.network.RetrofitService;
import com.example.mvp_demo.mvpView.IDailyStoryView;
import com.example.mvp_demo.utils.Logger;
import com.example.mvp_demo.widget.EmptyLayout;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yangfan on 2017/6/13.
 */

public class DailyStoryPresenter implements IBasePresenter {

    private static final String TAG = DailyStoryPresenter.class.getSimpleName();
    private IDailyStoryView mView;
    private String date;

    public DailyStoryPresenter(IDailyStoryView mView) {
        this.mView = mView;
    }

    @Override
    public void requestData(final boolean isRefresh) {
        RetrofitService.getNewsService()
                .getLatestDailyStories()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(
                        new Action0() {
                            @Override
                            public void call() {
                                if (!isRefresh) {
                                    mView.showLoading();
                                }
                            }
                        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyStories>() {
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
                    public void onNext(DailyStories stories) {
                        if(stories != null){
                            mView.showDailyStory(stories);
                            date = stories.getDate();
                        }
                    }
                });
    }

    @Override
    public void requestMoreData() {
        RetrofitService.getNewsService()
                .getBeforeDailyStories(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyStories>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DailyStories stories) {
                        mView.showDailyStory(stories);
                        date = stories.getDate();
                    }
                });
    }
}
