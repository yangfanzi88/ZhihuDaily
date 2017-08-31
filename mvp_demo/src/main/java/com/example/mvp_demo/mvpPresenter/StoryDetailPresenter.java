package com.example.mvp_demo.mvpPresenter;

import com.example.mvp_demo.mvpMode.beans.StoryDetail;
import com.example.mvp_demo.mvpMode.network.RetrofitService;
import com.example.mvp_demo.mvpView.IStoryDetailView;
import com.example.mvp_demo.utils.Logger;
import com.example.mvp_demo.widget.EmptyLayout;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by yangfan on 2017/8/27.
 */

public class StoryDetailPresenter implements IBasePresenter {
    private static String TAG = StoryDetailPresenter.class.getSimpleName();
    private IStoryDetailView mView;
    private int mStoryId;

    public StoryDetailPresenter(IStoryDetailView mView, int mStoryId) {
        this.mView = mView;
        this.mStoryId = mStoryId;
    }

    @Override
    public void requestData(final boolean isRefresh) {
        RetrofitService.getNewsService()
                .getStoryDetail(mStoryId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if(!isRefresh){
                            mView.showLoading();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StoryDetail>() {
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
                    public void onNext(StoryDetail detail) {
                        if(detail != null){
                            mView.showDetail(detail);
                        }
                    }
                });
    }

    @Override
    public void requestMoreData() {

    }
}
