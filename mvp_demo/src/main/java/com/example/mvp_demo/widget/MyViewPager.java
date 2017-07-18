package com.example.mvp_demo.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.example.mvp_demo.utils.Logger;

import java.lang.ref.WeakReference;

/**
 * Created by yangfan on 2017/6/19.
 */

public class MyViewPager extends ViewPager {
    private static final String TAG = MyViewPager.class.getSimpleName();
    private static final int WHAT_SCROLL = 0;
    private final long mDelayTime = 5000;
    private boolean mIsAutoScroll;
    private MyHandler mHandler;

    public MyViewPager(Context context) {
        super(context);
        mHandler = new MyHandler(this);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHandler = new MyHandler(this);
    }

    public void startAutoScroll(){
        mIsAutoScroll = true;
        sendScrollMessage(mDelayTime);
    }

    public void stopAutoScroll(){
        mIsAutoScroll = false;
        mHandler.removeMessages(WHAT_SCROLL);
    }

    public boolean isAutoScrolling(){
        return mIsAutoScroll;
    }
    private void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int count;
        if (adapter == null || (count = adapter.getCount()) < 1) {
            stopAutoScroll();
            return;
        }
        if (currentItem < count) {
            currentItem++;
        }
        if (currentItem == count) {
            currentItem = 0;
        }
        Logger.i(TAG, "currentItem : " + currentItem);
        setCurrentItem(currentItem);
    }

    private void sendScrollMessage(long delayTimeMills) {
        if (mIsAutoScroll) {
            mHandler.removeMessages(WHAT_SCROLL);
            mHandler.sendEmptyMessageDelayed(WHAT_SCROLL, delayTimeMills);
        }
    }

    private static class MyHandler extends Handler{
        WeakReference<MyViewPager> mViewPager;

        public MyHandler(MyViewPager mViewPager) {
            this.mViewPager = new WeakReference<>(mViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             MyViewPager viewPager = mViewPager.get();
            if (msg.what == WHAT_SCROLL && viewPager != null) {
                viewPager.scrollOnce();
                viewPager.sendScrollMessage(viewPager.mDelayTime);
            }
        }
    }
}
