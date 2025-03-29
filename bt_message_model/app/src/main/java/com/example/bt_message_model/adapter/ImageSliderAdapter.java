package com.example.bt_message_model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.example.bt_message_model.R;
import com.example.bt_message_model.model.ImagesSlider;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {
    private Context context;
    private List<ImagesSlider> imageList;

    public ImageSliderAdapter(Context context, List<ImagesSlider> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);

        Glide.with(context).load(imageList.get(position).getAvatar()).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

