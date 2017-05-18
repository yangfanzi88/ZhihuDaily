package com.aile.fanyangsz.zhihudaily.ui.fragment;

import android.widget.ListView;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.adapter.VideoAdapter;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Category;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Feature;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Issue;
import com.aile.fanyangsz.zhihudaily.support.beans.video.Item;
import com.aile.fanyangsz.zhihudaily.support.netWork.HttpSDK;
import com.aile.fanyangsz.zhihudaily.ui.base.BaseFragment;
import com.aile.fanyangsz.zhihudaily.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by fanyang.sz on 2016/11/24.
 */

public class ThirdFragment extends BaseFragment {

    @BindView(R.id.fragment_refreshLayout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.fragment_list)
    ListView mListView;

    private List<String> mCategoryNameList = new ArrayList<>();
    private List<List<Item>> mItemsList = new ArrayList<>();
    private VideoAdapter mVideoAdapter;
    private int count = 0 , categoryCount = 0;

    @Override
    protected int inflateContentView() {
        return R.layout.layout_content_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void requestData() {
//        HttpSDK.getInstance().getVideoTodayFeature(callBack);
        HttpSDK.getInstance().getVideoCategory(categoryCallBack);
    }

    HttpSDK.onVideoCategoryCallBack categoryCallBack = new HttpSDK.onVideoCategoryCallBack() {
        @Override
        public void onSuccess(List<Category> categoryList) {
            categoryCount = categoryList.size();

            HttpSDK.getInstance().getVideoTodayFeature(todayFeatureCallBack);

            for (Category category : categoryList) {
//                mCategoryNameList.add(category.getName());
                HttpSDK.getInstance().getVideoFeature(category.getName(), featureCallBack);
            }
        }

        @Override
        public void onError(String s) {

        }
    };

    HttpSDK.onVideoTodayFeatureCallBack todayFeatureCallBack = new HttpSDK.onVideoTodayFeatureCallBack() {
        @Override
        public void onSuccess(Feature beans) {
            mItemListAdd(0, beans.getIssueList().get(0).getItemList(), "每日精选");
        }

        @Override
        public void onError(String s) {

        }
    };

    HttpSDK.onVideoFeatureCallBack featureCallBack = new HttpSDK.onVideoFeatureCallBack() {
        @Override
        public void onSuccess(Issue issueBean, String categoryName) {
            if (issueBean != null && issueBean.getItemList().size() > 0) {
                mItemListAdd(1, issueBean.getItemList(), categoryName);
            }
        }

        @Override
        public void onError(String s) {

        }
    };


    private synchronized void mItemListAdd(int flag, List<Item> item, String categoryName) {
        if (flag == 0) {
            mItemsList.add(0, item);
            mCategoryNameList.add(0, categoryName);
        } else {
            mItemsList.add(item);
            mCategoryNameList.add(categoryName);
        }
        count += 1;

        if(count == categoryCount+1){
            if (mVideoAdapter == null) {
                mVideoAdapter = new VideoAdapter(getActivity(), mCategoryNameList, mItemsList);
                mListView.setAdapter(mVideoAdapter);
            } else {
                mVideoAdapter.notifyDataSetChanged();
            }
        }
    }

}
