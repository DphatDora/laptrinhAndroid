package com.example.bt_viewflipper;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bt_viewflipper.adapter.ImagesViewPager2Adapter;
import com.example.bt_viewflipper.model.Images;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class CircleIndicator3Activity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Images> imagesList1;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == imagesList1.size() - 1){
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circleindicator3_viewpager2);
        viewPager2 = findViewById(R.id.viewpager2);
        circleIndicator3 = findViewById(R.id.circle_indicator3);

        imagesList1 = getListImages();
        ImagesViewPager2Adapter adapter1 = new ImagesViewPager2Adapter(this, imagesList1);
        viewPager2.setAdapter(adapter1);

        circleIndicator3.setViewPager(viewPager2);

        // lang nghe view pager chuyen trang
        handler.postDelayed(runnable, 3000);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        handler.removeCallbacks(runnable);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        handler.postDelayed(runnable, 3000);
//    }

    private List<Images> getListImages() {
        List<Images> imagesList = new ArrayList<>();
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/quangcao.png"));
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/coffee.jpg"));
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/companypizza.jpeg"));
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/themoingon.jpeg"));
        return imagesList;
    }

}
