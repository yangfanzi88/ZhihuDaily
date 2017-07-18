package com.example.mvp_demo.mvpMode.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangfan on 2017/3/26.
 */

public class DailyStories extends BaseBean implements Serializable {

    private String date;
    private List<DailyStory> stories;
    private List<DailyStory> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DailyStory> getStories() {
        return stories;
    }

    public void setStories(List<DailyStory> stories) {
        this.stories = stories;
    }

    public List<DailyStory> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<DailyStory> top_stories) {
        this.top_stories = top_stories;
    }
}
