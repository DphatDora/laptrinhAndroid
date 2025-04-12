package com.example.bt_firebasevideo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private ViewPager2 viewPage2;
    private VideosFireBaseAdapter videosFireBaseAdapter;
    DatabaseReference userRef;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        viewPage2 = findViewById(R.id.viewpager);
        getVideos();

        uid = getIntent().getStringExtra("USER_ID");
        ivProfile = findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("USER_ID", uid);
            startActivity(intent);
        });
        userRef = FirebaseDatabase.getInstance("https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users").child(uid);
        // Đọc dữ liệu user từ Firebase
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                if (user != null) {

                    if (user.getAvt() != null && !user.getAvt().isEmpty()) {
                        Glide.with(MainActivity.this).load(user.getAvt()).into(ivProfile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lỗi tải thông tin: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getVideos(){
        DatabaseReference mDataBase = FirebaseDatabase.getInstance("https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("videos");
        mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseCheck", "Số lượng video: " + snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });
        FirebaseRecyclerOptions<VideoModel> options = new FirebaseRecyclerOptions.Builder<VideoModel>()
                .setQuery(mDataBase, VideoModel.class).build();
        videosFireBaseAdapter = new VideosFireBaseAdapter(options);
        viewPage2.setOrientation(viewPage2.ORIENTATION_VERTICAL);
        viewPage2.setAdapter(videosFireBaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        videosFireBaseAdapter.startListening();
    }
}