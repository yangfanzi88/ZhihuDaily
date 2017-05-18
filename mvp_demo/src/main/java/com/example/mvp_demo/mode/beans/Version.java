package com.example.mvp_demo.mode.beans;

import java.io.Serializable;

/**
 * Created by yangfan on 2017/3/26.
 */

public class Version extends BaseBean implements Serializable{
    int status;
    String msg;
    String url;
    String latest;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }
}
