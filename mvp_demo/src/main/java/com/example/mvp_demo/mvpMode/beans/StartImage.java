package com.example.mvp_demo.mvpMode.beans;

import java.io.Serializable;

/**
 * Created by yangfan on 2017/3/26.
 */

public class StartImage extends BaseBean implements Serializable{
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
