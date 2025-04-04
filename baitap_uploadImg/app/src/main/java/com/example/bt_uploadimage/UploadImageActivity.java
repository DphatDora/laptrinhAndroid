package com.example.bt_uploadimage;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {
    ApiService apiService;
    Button btnChoose, btnUpload;
    ImageView imageViewChoose, imageViewUpload;
    EditText etUsername;
    TextView tvUsername;
    private Uri mUri;
    private ProgressDialog mProgressDialog;
    public static final int MY_REQUEST_CODE = 100;
    public static final String TAG = UploadImageActivity.class.getName();

    public static String[] storage_permissions = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    private void checkPermission(){

//        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//            openGallery();
//        }
//        else{
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                requestPermissions(storage_permissions_33, MY_REQUEST_CODE);
//            }
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(storage_permissions_33, MY_REQUEST_CODE);
            } else {
                openGallery();
            }
        } else { // Android 12 trở xuống
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(storage_permissions, MY_REQUEST_CODE);
            } else {
                openGallery();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult: " + result);
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageViewChoose.setImageBitmap(bitmap);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.upload_image);
        anhXa();

        Bitmap bitmap = getIntent().getParcelableExtra("profileImage");
        if (bitmap != null) {
            imageViewChoose.setImageBitmap(bitmap);
        }

        // khởi tạo progressbar
        mProgressDialog = new ProgressDialog(UploadImageActivity.this);
        mProgressDialog.setMessage("Uploading...");

        btnChoose.setOnClickListener(v -> {
            checkPermission();
            //chooseImage();
        });

        btnUpload.setOnClickListener(v -> {
            if(mUri != null){
                uploadImage();
            }
        });
    }

    private void anhXa(){
        btnChoose = findViewById(R.id.btn_choose);
        btnUpload = findViewById(R.id.btn_upload);
        imageViewChoose = findViewById(R.id.imgChoose);
        imageViewUpload = findViewById(R.id.imgMultipart);
        etUsername = findViewById(R.id.et_username);
        tvUsername = findViewById(R.id.tv_username);
    }

    public void uploadImage() {
        mProgressDialog.show();

        // Khai báo biến và setText nếu có
        String username = etUsername.getText().toString().trim();
        RequestBody requestUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);

        // Tạo request body từ file ảnh
        String IMAGE_PATH = RealPathUtil.getPathFromUri(this, mUri);
        File file = new File(IMAGE_PATH);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part để gửi file ảnh
        MultipartBody.Part partBodyAvatar = MultipartBody.Part.createFormData(Const.MY_IMAGES, file.getName(), requestFile);

        apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        apiService.upload(requestUsername, partBodyAvatar).enqueue(new Callback<List<ImageUpload>>() {
                    @Override
                    public void onResponse(Call<List<ImageUpload>> call, Response<List<ImageUpload>> response) {
                        mProgressDialog.dismiss();

                        List<ImageUpload> imageUpload = response.body();
                        if (imageUpload != null && imageUpload.size() > 0) {
                            for (int i = 0; i < imageUpload.size(); i++) {
                                tvUsername.setText(imageUpload.get(i).getUsername());
                                Glide.with(UploadImageActivity.this)
                                        .load(imageUpload.get(i).getAvatar())
                                        .into(imageViewUpload);
                            }
                            Toast.makeText(UploadImageActivity.this, "Thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UploadImageActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ImageUpload>> call, Throwable t) {
                        mProgressDialog.dismiss();
                        Log.e("TAG", "onFailure: " + t.getMessage());
                        Toast.makeText(UploadImageActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}