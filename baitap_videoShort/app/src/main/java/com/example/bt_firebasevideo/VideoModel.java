package com.example.bt_firebasevideo;

import java.io.Serializable;

public class VideoModel implements Serializable {
    private String title;
    private String desc;
    private String url;
    private int likeCount;
    private String userId;

    public VideoModel() {

    }

    public VideoModel(String title, String desc, String url, int likeCount, String userId) {
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.likeCount = likeCount;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
