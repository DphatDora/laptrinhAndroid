package com.example.bt_viewflipper;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bt_viewflipper.adapter.ImagesViewPageAdapter;
import com.example.bt_viewflipper.model.Images;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class CirlceIndicatorActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private List<Images> imagesList;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager.getCurrentItem() == imagesList.size() - 1){
                viewPager.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circleindicator_viewpager);
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);

        imagesList = getListImages();
        ImagesViewPageAdapter adapter = new ImagesViewPageAdapter(this, imagesList);
        viewPager.setAdapter(adapter);

        circleIndicator.setViewPager(viewPager);

        // call runnable
        handler.postDelayed(runnable, 3000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    private List<Images> getListImages() {
        List<Images> imagesList = new ArrayList<>();
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/quangcao.png"));
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/coffee.jpg"));
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/companypizza.jpeg"));
        imagesList.add(new Images("http://app.iotstar.vn:8081/appfoods/flipper/themoingon.jpeg"));
        return imagesList;
    }

}
