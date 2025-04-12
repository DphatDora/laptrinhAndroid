package com.example.bt_videoapiserver;

import java.io.Serializable;

public class VideoModel implements Serializable {
    private int id;
    private String title;
    private String desc;
    private String url;

    public VideoModel(int id, String title, String url, String desc) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.desc = desc;
    }
    public VideoModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
