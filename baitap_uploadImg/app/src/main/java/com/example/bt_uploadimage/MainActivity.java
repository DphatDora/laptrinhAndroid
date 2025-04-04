package com.example.bt_uploadimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ivProfile = findViewById(R.id.iv_profile);
        btnLogout = findViewById(R.id.btn_logout);

        ivProfile.setOnClickListener(v -> {
            ivProfile.buildDrawingCache();
            Bitmap bitmap = ivProfile.getDrawingCache();

            Intent intent = new Intent(MainActivity.this, UploadImageActivity.class);
            intent.putExtra("profileImage", bitmap);
            startActivity(intent);
        });
    }
}
