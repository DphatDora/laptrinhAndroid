package com.example.testadapterviewapp;

public class MonHoc {
    public MonHoc(String name, String desc, int pic) {
        this.name = name;
        this.desc = desc;
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    private String name;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public int getPic() {
        return pic;
    }

    private int pic;
}
