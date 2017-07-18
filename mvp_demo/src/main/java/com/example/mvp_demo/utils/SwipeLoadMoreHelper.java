package com.example.mvp_demo.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvp_demo.R;

/**
 * Created by yangfan on 2017/7/9.
 */

public class SwipeLoadMoreHelper extends RecyclerView.OnScrollListener {
    /************
     * 加载更多的方法区
     **********/
    private Context mContext;
    private OnRequestDataListener onRequestDataListener;
    private boolean mIsLoadMoreEnable;
    private boolean mIsLoadingNow;
    private boolean mIsNoMoreData;
    private String mLoadingStr;
    private View mLoadingView;
    private TextView mLoadingDesc;
    private ProgressBar mLoadingIcon;

    public SwipeLoadMoreHelper(Context context) {
        this.mContext = context;
    }

    public void setRequestDataListener(OnRequestDataListener listener) {
        this.onRequestDataListener = listener;
        if (!mIsLoadMoreEnable) {
            this.enableLoadMore(true);
        }
    }

    public void enableLoadMore(boolean isEnable) {
        this.mIsLoadMoreEnable = isEnable;
        _initLoadingView(mContext);
    }


    public void setLoadDesc(String desc) {
        _initLoadingView(mContext);
        mLoadingStr = desc;
        mLoadingDesc.setText(mLoadingStr);
    }

    public void setLoadColor(int color) {
        mLoadingDesc.setTextColor(color);
    }

    /**
     * 加载完成
     */
    public void loadComplete() {
        mIsLoadingNow = false;
    }

    /**
     * 没有更多数据，后面不再加载数据
     */
    public void noMoreData() {
        mIsLoadingNow = false;
        mIsNoMoreData = true;
        mLoadingIcon.setVisibility(View.GONE);
        mLoadingDesc.setText(R.string.no_more_data);
    }

    /**
     * 加载数据异常，重新进入可再加载数据
     */
    public void loadAbnormal() {
        mIsLoadingNow = false;
        mLoadingIcon.setVisibility(View.GONE);
        mLoadingDesc.setText(R.string.load_abnormal);
    }

    private void _initLoadingView(Context mContext) {
        if (mLoadingView == null) {
            mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.layout_load_more, null);
            mLoadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            mLoadingDesc = (TextView) mLoadingView.findViewById(R.id.tv_loading_desc);
            mLoadingIcon = (ProgressBar) mLoadingView.findViewById(R.id.iv_loading_icon);
            mLoadingStr = mContext.getResources().getString(R.string.loading_desc);
            mLoadingDesc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mIsLoadingNow && !mIsNoMoreData) {
                        _loadMore();
                    }
                }
            });
        }
    }

    private void _loadMore() {
        if (!mIsLoadingNow && onRequestDataListener != null && !mIsNoMoreData) {
            if (mLoadingIcon.getVisibility() == View.GONE) {
                mLoadingIcon.setVisibility(View.VISIBLE);
                mLoadingDesc.setText(mLoadingStr);
            }
            mIsLoadingNow = true;
            onRequestDataListener.onLoadMore();
        }
    }

    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
//            onLoadNextPage(recyclerView);
            if(onRequestDataListener != null){
                onRequestDataListener.onLoadMore();
            }
        }
    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    public enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }
}
