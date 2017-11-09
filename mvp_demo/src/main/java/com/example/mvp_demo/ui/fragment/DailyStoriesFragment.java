package com.example.mvp_demo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerDailyStoryComponent;
import com.example.mvp_demo.injector.modules.DailyStoryModule;
import com.example.mvp_demo.mvpMode.beans.DailyStories;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpView.IDailyStoryView;
import com.example.mvp_demo.ui.activity.StoryDetailActivity;
import com.example.mvp_demo.ui.adapter.DailyStoryAdapter;
import com.example.mvp_demo.ui.adapter.OnItemClickListener;
import com.example.mvp_demo.utils.Logger;
import com.example.mvp_demo.utils.OnRequestDataListener;
import com.example.mvp_demo.utils.SwipeLoadMoreHelper;
import com.example.mvp_demo.widget.MyViewPager;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/6/12.
 */

public class DailyStoriesFragment extends BaseFragment<IBasePresenter> implements IDailyStoryView,OnItemClickListener {

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
        mStoryAdapter.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(View view, int position) {
        StoryDetailActivity.launcherActivity(this.getActivity(),this.getClass(),String.valueOf(position));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recyclerView != null) {
            Logger.i(TAG, "recyclerView != null");
            View view = recyclerView.findViewById(R.id.viewPager);
            if (view != null) {
                Logger.i(TAG, "MyViewPager startAutoScroll");
                ((MyViewPager) view).startAutoScroll();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (recyclerView != null) {
            Logger.i(TAG, "recyclerView !=null");
            View view = recyclerView.findViewById(R.id.viewPager);
            if (view != null) {
                Logger.i(TAG, "MyViewPager stopAutoScroll");
                ((MyViewPager) view).stopAutoScroll();
            }
        }
    }
}
