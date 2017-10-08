package com.example.mvp_demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mvp_demo.R;
import com.example.mvp_demo.mvpMode.beans.DailyStories;
import com.example.mvp_demo.mvpMode.beans.DailyStory;
import com.example.mvp_demo.widget.CirclePageIndicator;
import com.example.mvp_demo.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfan on 2017/6/14.
 */

public class DailyStoryAdapter extends RecyclerView.Adapter implements View.OnClickListener, OnItemClickListener{

    private static final String TAG = DailyStoryAdapter.class.getSimpleName();

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_DATE = 1;
    private static final int TYPE_STORY = 2;

    private List<DailyStory> dailyStoryList;
    private List<DailyStory> topStoryList;

    private OnItemClickListener clickListener;
    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public DailyStoryAdapter() {
        dailyStoryList = new ArrayList<>();
        topStoryList = new ArrayList<>();
    }

    public void setStories(DailyStories stories){
        //为第一次添加进来的Topstory加上View
        if(stories.getTop_stories()!=null && stories.getTop_stories().size()!=0){
            //如果新添加进来的stories的topStory不为空说明是下拉刷新，要清空所有list
            dailyStoryList.clear();
            topStoryList.clear();


            DailyStory head = new DailyStory();
            head.setViewType(TYPE_HEAD);
            dailyStoryList.add(head);
            topStoryList.addAll(stories.getTop_stories());
        }
        //每一次添加story的时候，先把date当作一个item加进来；
        DailyStory date = new DailyStory();
        date.setViewType(TYPE_DATE);
        date.setTitle(stories.getDate());
        dailyStoryList.add(date);
        //然后把为个普通的story添加到list
        for(DailyStory story:stories.getStories()){
            story.setViewType(TYPE_STORY);
            dailyStoryList.add(story);
        }

        notifyDataSetChanged();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof HeaderViewPagerHolder) {
            HeaderViewPagerHolder pagerHolder = (HeaderViewPagerHolder) holder;
            if (!pagerHolder.isAutoScrolling()) {
                pagerHolder.startAutoScroll();
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof HeaderViewPagerHolder) {
            HeaderViewPagerHolder pagerHolder = (HeaderViewPagerHolder) holder;
            if (pagerHolder.isAutoScrolling()) {
                pagerHolder.stopAutoScroll();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View itemView = null;
        switch (viewType){
            case TYPE_HEAD:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header_viewpager,parent,false);
                viewHolder =  new HeaderViewPagerHolder(itemView,topStoryList);
                ((HeaderViewPagerHolder)viewHolder).setItemListener(this);
                break;
            case TYPE_DATE:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_date_item,parent,false);
                viewHolder = new DateViewHolder(itemView);
                break;
            case TYPE_STORY:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_content_item,parent,false);
                viewHolder = new StoryContentHolder(itemView);
                break;
        }
        if(itemView != null && viewType != TYPE_DATE){
            itemView.setOnClickListener(this);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        DailyStory story = dailyStoryList.get(position);
        holder.itemView.setTag(story.getId());
        switch(type){
            case TYPE_HEAD:
                ((HeaderViewPagerHolder)holder).bindHeaderView();
                break;
            case TYPE_DATE:
                ((DateViewHolder)holder).bindDateView(story);
                break;
            case TYPE_STORY:
                ((StoryContentHolder)holder).bindStoryView(story);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dailyStoryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dailyStoryList.get(position).getViewType();
    }

    @Override
    public void onClick(View v) {
        if(clickListener != null){
            clickListener.onItemClick(v, (int)v.getTag());
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if(clickListener != null){
            clickListener.onItemClick(view, position);
        }
    }

    public static class HeaderViewPagerHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.indicator)
        CirclePageIndicator mIndicator;
        @BindView(R.id.viewPager)
        MyViewPager mViewPager;
        private HeaderViewPagerAdapter mPagerAdapter;

        private List<DailyStory> storyList;

        public HeaderViewPagerHolder(View itemView, List<DailyStory> storyList) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.storyList = storyList;
            mPagerAdapter = new HeaderViewPagerAdapter(this.storyList);
        }

        public void bindHeaderView(){
            if(mViewPager.getAdapter() == null){
                mViewPager.setAdapter(mPagerAdapter);
                mIndicator.setViewPager(mViewPager);
            }else {
                mPagerAdapter.notifyDataSetChanged();
            }
        }

        public void setItemListener(OnItemClickListener listener){
            mPagerAdapter.setListener(listener);
        }

        public boolean isAutoScrolling() {
            if (mViewPager != null) {
                return mViewPager.isAutoScrolling();
            }
            return false;
        }

        public void stopAutoScroll() {
            if (mViewPager != null) {
                mViewPager.stopAutoScroll();
            }
        }

        public void startAutoScroll() {
            if (mViewPager != null) {
                mViewPager.startAutoScroll();
            }
        }
    }
    public static class DateViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.date)
        TextView storyDate;
        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindDateView(DailyStory story){
            storyDate.setText(story.getTitle());
        }
    }

    public static class StoryContentHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.story_content_cardView)
        CardView cardView;
        @BindView(R.id.story_content)
        RelativeLayout relativeLayout;
        @BindView(R.id.story_content_image)
        ImageView contentImage;
        @BindView(R.id.story_content_multiPic)
        ImageView multiImage;
        @BindView(R.id.story_content_text)
        TextView contentText;

        private Context mContext;

        public StoryContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }

        public void bindStoryView(DailyStory story){
            contentText.setText(story.getTitle());
            String imageUrl = story.getImages() == null ? "" : story.getImages().get(0);
            if (TextUtils.isEmpty(imageUrl) || story.isMultipic()) {
                multiImage.setVisibility(View.GONE);
            } else if (story.isMultipic()) {
                multiImage.setVisibility(View.VISIBLE);
            }

            if (TextUtils.isEmpty(imageUrl)) {
                contentImage.setVisibility(View.GONE);
            } else {
                contentImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(imageUrl).centerCrop().into(contentImage);
            }
        }
    }

}
