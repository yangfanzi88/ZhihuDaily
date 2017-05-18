package com.example.mvp_demo.mode.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangfan on 2017/3/28.
 */

public class DailyThemeStories extends BaseBean implements Serializable {
    List<DailyStory> stories;
    String description;
    String background;
    String color;
    String name;
    String image;
    String image_source;
    List<Editor> editors;


    public List<DailyStory> getStories() {
        return stories;
    }

    public void setStories(List<DailyStory> stories) {
        this.stories = stories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }
}
