package com.aile.fanyangsz.zhihudaily.support.beans;

import java.io.Serializable;

/**
 * Created by fanyang.sz on 2016/11/4.
 */

public class NewsTopicStoryBean extends BaseBean implements Serializable {
    private String title;
    private String ga_prefix;
    private String image;
    private boolean multipic;
    private int type;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
