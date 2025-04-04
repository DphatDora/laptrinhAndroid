package com.example.bt_uploadimage;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("updateimages.php")
    Call<List<ImageUpload>> upload(@Part(Const.MY_USERNAME) RequestBody username, @Part MultipartBody.Part avatar);
}
