package com.example.mvp_demo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerDailyStoryComponent;
import com.example.mvp_demo.injector.modules.DailyStoryModule;
import com.example.mvp_demo.mvpMode.beans.DailyStories;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpView.IDailyStoryView;
import com.example.mvp_demo.ui.adapter.DailyStoryAdapter;
import com.example.mvp_demo.utils.Logger;
import com.example.mvp_demo.utils.OnRequestDataListener;
import com.example.mvp_demo.utils.SwipeLoadMoreHelper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/6/12.
 */

public class DailyStoriesFragment extends BaseFragment<IBasePresenter> implements IDailyStoryView {

    private final static String TAG = DailyStoriesFragment.class.getSimpleName();

    @Inject
    DailyStoryAdapter mStoryAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initInjector() {
        DaggerDailyStoryComponent.builder()
                .applicationComponent(getAppComponent())
                .dailyStoryModule(new DailyStoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(recyclerView.getAdapter() == null){
            recyclerView.setAdapter(mStoryAdapter);
        }
        SwipeLoadMoreHelper helper = new SwipeLoadMoreHelper(mContext);
        helper.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.requestMoreData();
            }
        });
        recyclerView.setOnScrollListener(helper);

    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.requestData(isRefresh);
        Logger.e(TAG, "updateView 调用一次");
    }

    @Override
    public void showDailyStory(DailyStories stories) {
        mStoryAdapter.setStories(stories);
    }


}
