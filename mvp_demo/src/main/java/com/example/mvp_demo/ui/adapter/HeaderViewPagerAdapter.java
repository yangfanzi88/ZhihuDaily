package com.example.mvp_demo.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvp_demo.mvpMode.beans.DailyStory;
import com.example.mvp_demo.widget.StoryHeaderView;

import java.util.List;

/**
 * Created by yangfan on 2017/6/23.
 */

public class HeaderViewPagerAdapter extends PagerAdapter implements View.OnClickListener{

    private final List<DailyStory> storyList;
    private int mChildCount;
    private OnItemClickListener mListener;

    public HeaderViewPagerAdapter(List<DailyStory> storyList) {
        this.storyList = storyList;
    }

    @Override
    public int getCount() {
        return storyList == null? 0 : storyList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        StoryHeaderView storyHeaderView = StoryHeaderView.newInstance(container.getContext());
        final DailyStory story = storyList.get(position);
        storyHeaderView.bindData(story.getTitle(), story.getImage(), story.getImage());
        storyHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                IntentUtils.intentToStoryActivity((Activity) v.getContext(), story);
            }
        });
        container.addView(storyHeaderView);

        storyHeaderView.setTag(story.getId());
        storyHeaderView.setOnClickListener(this);
        return storyHeaderView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((StoryHeaderView) object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mChildCount = getCount();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        mListener.onItemClick(v, position);
    }

    public void setListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
