package com.aile.fanyangsz.zhihudaily.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.adapter.NewsAdapter;
import com.aile.fanyangsz.zhihudaily.support.bannerImageLoader.GlideImageLoader;
import com.aile.fanyangsz.zhihudaily.support.beans.NewsBeans;
import com.aile.fanyangsz.zhihudaily.support.beans.NewsStoryBean;
import com.aile.fanyangsz.zhihudaily.support.beans.NewsTopicStoryBean;
import com.aile.fanyangsz.zhihudaily.support.netWork.HttpSDK;
import com.aile.fanyangsz.zhihudaily.ui.activity.NewsDetailActivity;
import com.aile.fanyangsz.zhihudaily.ui.base.BaseFragment;
import com.aile.fanyangsz.zhihudaily.utils.AndroidUtils;
import com.aile.fanyangsz.zhihudaily.widget.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fanyang.sz on 2016/11/4.
 */


public class FirstFragment extends BaseFragment
        implements
            RefreshLayout.OnLoadListener,
            RefreshLayout.OnRefreshListener
            {

    @BindView(R.id.fragment_refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.fragment_list)
    ListView mListView;

    Banner mBanner;

    List<String> images = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    String newsDay = null;
    NewsAdapter adapter;
    NewsBeans currentBeans;
    @Override
    protected int inflateContentView() {
        return R.layout.layout_content_fragment;
    }

    @Override
    protected void initView() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadListener(this);
    }

    private void setListView() {
        adapter = new NewsAdapter(getActivity(), currentBeans);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(mListView.getHeaderViewsCount()==0){
            View view = View.inflate(getActivity(), R.layout.layout_first_item_header, null);
            mBanner = (Banner) view.findViewById(R.id.banner);
            mListView.addHeaderView(view);
        }
        setBanner();
        mListView.setOnItemClickListener(onItemClick);
    }

    private void setBanner() {
        // 首先设置banner
        // 设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        // 设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        // 设置图片集合
        mBanner.setImages(images);
        // 设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        // 设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(titles);
        // 设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        // 设置轮播时间
        mBanner.setDelayTime(3000);
        // 设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        // banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerClickListener(onClick);
    }

    @Override
    protected void requestData() {
        requestNews(null);
    }

    private void requestNews(String date) {
        HttpSDK.getInstance().getNews( date, callBack);
    }

    HttpSDK.onNewsCallBack callBack = new HttpSDK.onNewsCallBack() {
        @Override
        public void onSuccess(NewsBeans data) {
            if (data != null) {

                if (data.getTop_stories() != null && data.getTop_stories().size() > 0) {
                    images.clear();
                    titles.clear();
                    for (NewsTopicStoryBean bean : data.getTop_stories()) {
                        images.add(bean.getImage());
                        titles.add(bean.getTitle());
                    }

                }
                if (data.getStories() != null && data.getStories().size() > 0) {
                    NewsStoryBean storyBean = new NewsStoryBean();
                    if (TextUtils.isEmpty(newsDay)) {
                        mRefreshLayout.setRefreshing(false);
                        storyBean.setDayTime("今日热闻");
                        data.getStories().add(0,storyBean);
                        currentBeans = data;
                        setListView();
                    } else {
//                        mRefreshLayout.setLoading(false);
                        storyBean.setDayTime(AndroidUtils.string2Date(data.getDate()));
                        data.getStories().add(0,storyBean);
                        currentBeans.getStories().addAll(data.getStories());
                        adapter.notifyDataSetChanged();
                    }
                }
                newsDay = data.getDate();
            }

        }

        @Override
        public void onError(String s) {

        }
    };

    OnBannerClickListener onClick = new OnBannerClickListener() {
        @Override
        public void OnBannerClick(int position) {
            launchNewsDetail(currentBeans.getTop_stories().get(position-1).getId());
        }
    };

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position>0){
                NewsStoryBean storyBean = adapter.getdatas().getStories().get(position-1);
                int storyId = storyBean.getId();
                launchNewsDetail(storyId);
            }
        }
    };

    private void launchNewsDetail(int id){
        if(id<=0)
            return;
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("newsId",id);
        getActivity().startActivity(intent);
    }

    @Override
    public void onRefresh() {
        newsDay = null;
        requestNews(null);
    }

    @Override
    public void onLoad() {
        requestNews(newsDay);
    }

}
