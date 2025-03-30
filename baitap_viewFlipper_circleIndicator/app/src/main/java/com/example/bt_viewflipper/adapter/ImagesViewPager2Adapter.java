package com.example.bt_viewflipper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bt_viewflipper.R;
import com.example.bt_viewflipper.model.Images;

import java.util.List;

public class ImagesViewPager2Adapter extends RecyclerView.Adapter<ImagesViewPager2Adapter.ImagesViewHolder> {

    private List<Images> imagesList;
    private Context context;

    public ImagesViewPager2Adapter(Context context, List<Images> imagesList){
        this.context = context;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        Images images = imagesList.get(position);
        if (images == null){
            return;
        }
        Glide.with(holder.imageView.getContext())
                .load(images.getImgString()) // Nếu imgString là URL, đảm bảo nó là một đường dẫn hợp lệ.
                .placeholder(R.drawable.ic_launcher_background) // Ảnh hiển thị khi đang tải
                .error(R.drawable.ic_launcher_background) // Ảnh hiển thị nếu load thất bại
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(imagesList != null){
            return imagesList.size();
        }
        return 0;
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public ImagesViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }

}
