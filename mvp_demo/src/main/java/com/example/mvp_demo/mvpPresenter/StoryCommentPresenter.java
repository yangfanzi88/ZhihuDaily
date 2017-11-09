package com.example.mvp_demo.mvpPresenter;

import com.example.mvp_demo.mvpMode.beans.StoryComment;
import com.example.mvp_demo.mvpMode.network.RetrofitService;
import com.example.mvp_demo.mvpView.IStoryCommentView;
import com.example.mvp_demo.utils.Logger;
import com.example.mvp_demo.widget.EmptyLayout;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by yangfan on 2017/10/8.
 */

public class StoryCommentPresenter implements IBasePresenter {
    private static String TAG = StoryCommentPresenter.class.getSimpleName();
    private IStoryCommentView mView;
    private int mStoryId;

    public StoryCommentPresenter(IStoryCommentView mView, int storyId) {
        this.mView = mView;
        this.mStoryId = storyId;
    }

    @Override
    public void requestData(final boolean isRefresh) {
        RetrofitService.getNewsService()
                .getStoryLongComments(mStoryId)
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
                .subscribe(new Subscriber<StoryComment>() {
                    @Override
                    public void onCompleted() {
                        if(isRefresh){
                            mView.finishRefresh();
                        }else {
                            mView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG,e.toString() + " " + isRefresh);
                        e.printStackTrace();
                        if(isRefresh){
                            mView.finishRefresh();
                            // 可以提示对应的信息，但不更新界面
                        }else {
                            mView.showError(new EmptyLayout.OnRetryListener() {
                                @Override
                                public void onRetry() {
                                    requestData(false);
                                }
                            });
                        }
                    }

                    @Override
                    public void onNext(StoryComment storyComments) {
                        mView.showComment(storyComments);
                    }
                });
    }

    @Override
    public void requestMoreData() {

    }
}
