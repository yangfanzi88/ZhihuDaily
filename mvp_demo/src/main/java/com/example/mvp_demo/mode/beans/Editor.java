package com.example.mvp_demo.mode.beans;

import java.io.Serializable;

/**
 * Created by yangfan on 2017/3/28.
 */

public class Editor extends BaseBean implements Serializable {
    String bio;
    String title;
    String avatar;
    String name;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
