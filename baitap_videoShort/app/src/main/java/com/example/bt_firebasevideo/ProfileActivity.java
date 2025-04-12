package com.example.bt_firebasevideo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ImageView avatarImage;
    CardView btnBack;
    TextView nameText, emailText;
    RecyclerView recyclerView;
    Button uploadVideoBtn, signupBtn;
    static final int VIDEO_PICK_CODE = 101, IMAGE_PICK_CODE = 102, REQUEST_PERMISSION_CODE = 123;
    Uri selectedVideoUri, selectedImageUri;
    StorageReference videoStorageRef, avatarStorageRef;
    DatabaseReference userRef;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        signupBtn = findViewById(R.id.signupBtn);
        btnBack = findViewById(R.id.cvBack);
        avatarImage = findViewById(R.id.avatarImage);
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uploadVideoBtn = findViewById(R.id.uploadVideoBtn);
        videoStorageRef = FirebaseStorage.getInstance("gs://video-short-fe473.firebasestorage.app")
                .getReference("videos");
        uploadVideoBtn.setOnClickListener(v -> checkAndRequestPermission(true));

        avatarStorageRef = FirebaseStorage.getInstance("gs://video-short-fe473.firebasestorage.app")
                .getReference("avatars");

        uid = getIntent().getStringExtra("USER_ID");

        userRef = FirebaseDatabase.getInstance("https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users").child(uid);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                if (user != null) {
                    nameText.setText(user.getName());
                    emailText.setText(user.getEmail());

                    if (user.getAvt() != null && !user.getAvt().isEmpty()) {
//                        if (!isFinishing() && !isDestroyed()) {
//                            Glide.with(ProfileActivity.this).load(user.getAvt()).into(avatarImage);
//                        }
                        Glide.with(ProfileActivity.this).load(user.getAvt()).into(avatarImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Lỗi tải thông tin: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference videosRef = FirebaseDatabase.getInstance("https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("videos");

        videosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<VideoModel> userVideos = new ArrayList<>();
                for (DataSnapshot videoSnap : snapshot.getChildren()) {
                    VideoModel video = videoSnap.getValue(VideoModel.class);
                    if (video != null && video.getUserId().equals(uid)) {
                        userVideos.add(video);
                    }
                }
                recyclerView.setAdapter(new VideoAdapter(userVideos));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Lỗi tải video: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        avatarImage.setOnClickListener(v -> checkAndRequestPermission(false));
        btnBack.setOnClickListener(v -> finish());
        signupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void pickVideoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(Intent.createChooser(intent, "Chọn video"), VIDEO_PICK_CODE);
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_PICK_CODE && resultCode == RESULT_OK && data != null) {
            selectedVideoUri = data.getData();
            showVideoInfoDialog();
        }
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            uploadImageToFirebase();
        }
    }

    private void showVideoInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_video, null);
        EditText titleInput = view.findViewById(R.id.videoTitleInput);
        EditText descInput = view.findViewById(R.id.videoDescInput);
        Button uploadBtn = view.findViewById(R.id.uploadBtn);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        uploadBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String desc = descInput.getText().toString();
            if (!title.isEmpty() && selectedVideoUri != null) {
                uploadVideoToFirebase(title, desc, dialog);
            }
        });
    }

    private void uploadVideoToFirebase(String title, String desc, AlertDialog dialog) {
        if (selectedVideoUri == null || title.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn video và nhập tiêu đề!", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = System.currentTimeMillis() + ".mp4";
        StorageReference videoRef = videoStorageRef.child(uid).child(fileName);

        videoRef.putFile(selectedVideoUri)
                .addOnSuccessListener(taskSnapshot -> {

                    videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String videoUrl = uri.toString();

                        VideoModel video = new VideoModel(title, desc, videoUrl, 0, uid);

                        // Lưu object vào Realtime Database
                        DatabaseReference videosRef = FirebaseDatabase.getInstance(
                                        "https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("videos")
                                .push(); // push tạo ra ID tự động

                        videosRef.setValue(video)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(this, "Tải video thành công!", Toast.LENGTH_SHORT).show();
                                        if (!isFinishing() && !isDestroyed()) {
                                            dialog.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(this, "Lỗi lưu dữ liệu video", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Tải video thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



    private void uploadImageToFirebase() {
        if (selectedImageUri == null) return;

        String fileName = uid + "_avatar.jpg";
        StorageReference avatarRef = avatarStorageRef.child(fileName);

        avatarRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot ->
                        avatarRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            userRef.child("avt").setValue(imageUrl);
                            Toast.makeText(this, "Cập nhật avatar thành công!", Toast.LENGTH_SHORT).show();
                        })
                ).addOnFailureListener(e -> {
                    Toast.makeText(this, "Cập nhật avatar thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void checkAndRequestPermission(boolean isForVideo) {
        String permission;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = isForVideo ? android.Manifest.permission.READ_MEDIA_VIDEO : android.Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_PERMISSION_CODE);
        } else {
            if (isForVideo) {
                pickVideoFromGallery();
            } else {
                pickImageFromGallery();
            }
        }
    }
}


