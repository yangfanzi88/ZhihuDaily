package com.example.mvp_demo.mvpMode.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangfan on 2017/3/28.
 */

public class DailyThemes extends BaseBean implements Serializable {
    int limit;
    List<DailyTheme> subscribed;
    List<DailyTheme> others;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<DailyTheme> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<DailyTheme> subscribed) {
        this.subscribed = subscribed;
    }

    public List<DailyTheme> getOthers() {
        return others;
    }

    public void setOthers(List<DailyTheme> others) {
        this.others = others;
    }

    public class DailyTheme extends BaseBean implements Serializable{
        int color;
        String thumbnail;
        String description;
        String name;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
