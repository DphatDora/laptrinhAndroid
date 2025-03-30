package com.example.bt_viewflipper.model;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Images {
    private String imgString;

    public Images(String imgString) {
        this.imgString = imgString;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    // Phương thức sử dụng Glide để hiển thị ảnh trong ImageView
    public void getImage(Context context, ImageView imageView) {
        Glide.with(context).load(imgString).into(imageView);
    }
}
