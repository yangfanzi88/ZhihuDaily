package com.aile.fanyangsz.zhihudaily.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.aile.fanyangsz.zhihudaily.R;
import com.aile.fanyangsz.zhihudaily.support.adapter.PictureAdapter;
import com.aile.fanyangsz.zhihudaily.support.beans.PictureBeans;
import com.aile.fanyangsz.zhihudaily.support.netWork.HttpSDK;
import com.aile.fanyangsz.zhihudaily.ui.base.BaseFragment;
import com.aile.fanyangsz.zhihudaily.widget.RefreshLayout;

import butterknife.BindView;

/**
 * Created by fanyang.sz on 2016/11/10.
 */

public class SecondFragment extends BaseFragment
        implements
            SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.second_fragment_refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.re_second_list)
    RecyclerView mRecyclerView;

    private int page = 1;
    PictureAdapter adapter;
    PictureBeans currentBeans;

    @Override
    protected int inflateContentView() {
        return R.layout.layout_second_fragment;
    }

    @Override
    protected void initView() {
        mRefreshLayout.setOnRefreshListener(this);
//        mRefreshLayout.setOnLoadListener(this);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        // //设置布局的排版方向
        // layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void requestData() {
        HttpSDK.getInstance().getPicture(page, onPictureCallBack);
    }

    HttpSDK.onPictureCallBack onPictureCallBack = new HttpSDK.onPictureCallBack() {
        @Override
        public void onSuccess(PictureBeans beans) {
            if (beans != null && beans.getResults() != null) {
                if (page == 1) {
                    mRefreshLayout.setRefreshing(false);
                    currentBeans = beans;
                    adapter = new PictureAdapter(getActivity(), currentBeans);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
//                    mRefreshLayout.setLoading(false);
                    currentBeans.getResults().addAll(beans.getResults());
                    adapter.notifyDataSetChanged();
                }

            }

        }

        @Override
        public void onError(String s) {

        }
    };

    @Override
    public void onRefresh() {
        page = 1;
        requestData();
    }


    //由于RefreshLayout封装的只是listView的形式，所以不适用recycleView
//    @Override
//    public void onLoad() {
//        page++;
//        requestData();
//    }
}
