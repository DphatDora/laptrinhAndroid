package com.example.bt_viewflipper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.bt_viewflipper.R;
import com.example.bt_viewflipper.model.Images;

import java.util.List;

public class ImagesViewPagerAdapter extends PagerAdapter {
    private List<Images> imagesList;
    private Context context;

    public ImagesViewPagerAdapter(Context context, List<Images> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_images, container, false);
        ImageView imageView = view.findViewById(R.id.imgView);

        // Sử dụng Glide để tải ảnh từ URL
        Images image = imagesList.get(position);
        Glide.with(context).load(image.getImgString()).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imagesList != null ? imagesList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
