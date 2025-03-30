package com.example.bt_recyclerview_indicator;

import java.io.Serializable;

public class IconModel implements Serializable {
    private Integer imdId;
    private String desc;

    public IconModel(Integer imdId, String desc) {
        this.imdId = imdId;
        this.desc = desc;
    }

    public Integer getImdId() {
        return imdId;
    }

    public void setImdId(Integer imdId) {
        this.imdId = imdId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
