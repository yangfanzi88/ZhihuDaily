package com.aile.fanyangsz.zhihudaily.support.beans;

import java.util.List;

/**
 * Created by fanyang.sz on 2016/11/4.
 */

public class NewsBeans extends BaseBean {
    private String date;
    private List<NewsStoryBean> stories;
    private List<NewsTopicStoryBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<NewsStoryBean> getStories() {
        return stories;
    }

    public void setStories(List<NewsStoryBean> stories) {
        this.stories = stories;
    }

    public List<NewsTopicStoryBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<NewsTopicStoryBean> top_stories) {
        this.top_stories = top_stories;
    }
}
