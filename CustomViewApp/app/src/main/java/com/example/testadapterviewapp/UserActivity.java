package com.example.testadapterviewapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private RecyclerView rvMultipleViewType;
    private List<Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        rvMultipleViewType = findViewById(R.id.rv_multipe_view_type);
        mData = new ArrayList<>();
        mData.add(new UserModel("Ha Duc Phat", "Phu Yen"));
        mData.add(R.drawable.java1);
        mData.add("Text 0");
        mData.add("Text 1");

        mData.add(new UserModel("Dora chan", "Phu Yen"));
        mData.add("Text 2");
        mData.add(R.drawable.c);
        mData.add(R.drawable.dart);

        mData.add(new UserModel("Furina", "TP HCM"));
        mData.add("Text 3");
        mData.add("Text 4");

        mData.add(new UserModel("Nguyen Van A", "Quan 5"));
        mData.add(R.drawable.kotlin);

        CustomAdapter adapter = new CustomAdapter(this, mData);
        rvMultipleViewType.setAdapter(adapter);
        rvMultipleViewType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}
