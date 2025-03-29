package com.example.bt_message_model;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.bt_message_model.adapter.ImageSliderAdapter;
import com.example.bt_message_model.model.ImagesSlider;
import com.example.bt_message_model.model.MessageModel;
import com.example.bt_message_model.service.ApiService;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator indicator;
    private ImageSliderAdapter adapter;
    private List<ImagesSlider> imageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        indicator = findViewById(R.id.circle_indicator);

        loadImageSlider();
    }

    private void loadImageSlider() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.LoadImageSlider(1).enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MessageModel messageModel = response.body();
                    if (messageModel.isSuccess()) {
                        imageList = messageModel.getResult();
                        adapter = new ImageSliderAdapter(MainActivity.this, imageList);
                        viewPager.setAdapter(adapter);
                        indicator.setViewPager(viewPager);
                    } else {
                        Toast.makeText(MainActivity.this, "No images found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to load images", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

