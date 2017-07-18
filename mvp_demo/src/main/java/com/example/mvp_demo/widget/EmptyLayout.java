package com.example.mvp_demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mvp_demo.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangfan on 2017/5/24.
 */

public class EmptyLayout extends FrameLayout {

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1002;
    public static final int STATUS_NO_NET = 1003;
    public static final int STATUS_NO_DATA = 1004;
    private Context mContext;
    private int mEmptyStatus = STATUS_LOADING;
    private OnRetryListener mOnRetryListener;
    private int mBgColor;


    @BindView(R.id.empty_layout)
    FrameLayout mEmptyLayout;
    @BindView(R.id.rl_empty_container)
    FrameLayout mRlEmptyLayout;
    @BindView(R.id.tv_net_error)
    TextView mTvEmptyMessage;
    @BindView(R.id.empty_loading)
    ProgressBar mEmptyLoading;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    /**
     * EmptyLayout 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }

        View.inflate(mContext, R.layout.layout_empty_loading, this);
        ButterKnife.bind(this);
        mEmptyLayout.setBackgroundColor(mBgColor);
        _switchEmptyView();
    }

    /**
     * 设置为隐藏视图状态
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();

    }

    /**
     * 返回状态
     *
     * @return
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置异常消息
     *
     * @param msg
     */
    public void setEmptyMessage(String msg) {
        mTvEmptyMessage.setText(msg);
    }

    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyLayout.setVisibility(GONE);
                mEmptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mRlEmptyLayout.setVisibility(VISIBLE);
                mEmptyLoading.setVisibility(GONE);
                break;
        }
    }

    @OnClick(R.id.tv_net_error)
    public void onClick() {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    public void setRetryListener(OnRetryListener retryListener) {
        mOnRetryListener = retryListener;
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}
