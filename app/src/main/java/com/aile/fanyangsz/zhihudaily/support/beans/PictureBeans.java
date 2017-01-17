package com.aile.fanyangsz.zhihudaily.support.beans;

import java.util.List;

/**
 * Created by fanyang.sz on 2016/11/15.
 */

public class PictureBeans extends BaseBean {
    private boolean error;
    private List<PictureBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<PictureBean> getResults() {
        return results;
    }

    public void setResults(List<PictureBean> results) {
        this.results = results;
    }
}
