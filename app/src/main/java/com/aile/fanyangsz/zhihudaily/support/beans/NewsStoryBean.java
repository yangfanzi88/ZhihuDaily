package com.aile.fanyangsz.zhihudaily.support.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fanyang.sz on 2016/11/4.
 */

public class NewsStoryBean extends BaseBean implements Serializable {
    private String title;
    private String ga_prefix;
    private List<String> images;
    private boolean multipic;
    private int type;

    private String dayTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
}
