package com.aile.fanyangsz.zhihudaily.support.beans;

import java.io.Serializable;

/**
 * Created by fanyang.sz on 2016/11/4.
 */

public class BaseBean implements Serializable {
    private static final long serialVersionUID = 5947827332427115190L;

    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
