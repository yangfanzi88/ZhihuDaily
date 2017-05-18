package com.aile.fanyangsz.zhihudaily.support.beans;

/**
 * Created by fanyang.sz on 2016/12/15.
 */

public class WelcomeBean extends BaseBean {
    private String text;
    private String img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
