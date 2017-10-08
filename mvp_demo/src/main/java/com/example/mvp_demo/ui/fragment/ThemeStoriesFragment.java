package com.example.mvp_demo.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mvp_demo.R;
import com.example.mvp_demo.injector.component.DaggerThemeStoryComponent;
import com.example.mvp_demo.injector.modules.ThemeStoryModule;
import com.example.mvp_demo.mvpMode.beans.DailyThemeStories;
import com.example.mvp_demo.mvpPresenter.IBasePresenter;
import com.example.mvp_demo.mvpView.IThemeStoryView;
import com.example.mvp_demo.ui.activity.StoryDetailActivity;
import com.example.mvp_demo.ui.adapter.OnItemClickListener;
import com.example.mvp_demo.ui.adapter.ThemeStoryAdapter;
import com.example.mvp_demo.utils.OnRequestDataListener;
import com.example.mvp_demo.utils.SwipeLoadMoreHelper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yangfan on 2017/7/23.
 */

public class ThemeStoriesFragment extends BaseFragment<IBasePresenter> implements IThemeStoryView,OnItemClickListener {

    @Inject
    ThemeStoryAdapter mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initInjector() {
        DaggerThemeStoryComponent.builder()
                .applicationComponent(getAppComponent())
                .themeStoryModule(new ThemeStoryModule(this, getThemeNumber()))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(recyclerView.getAdapter() == null){
            recyclerView.setAdapter(mAdapter);
        }

        SwipeLoadMoreHelper helper = new SwipeLoadMoreHelper(mContext);
        helper.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                mPresenter.requestMoreData();
            }
        });
        recyclerView.setOnScrollListener(helper);

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        mPresenter.requestData(isRefresh);
    }


    @Override
    public void showThemeStory(DailyThemeStories stories) {
        mAdapter.setTheme(stories);
    }

    @Override
    public void onItemClick(View view, int position) {
        StoryDetailActivity.launcherActivity(this.getActivity(),this.getClass(),String.valueOf(position));
    }
}
