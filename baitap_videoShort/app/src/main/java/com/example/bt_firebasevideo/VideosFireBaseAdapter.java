package com.example.bt_firebasevideo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideosFireBaseAdapter extends FirebaseRecyclerAdapter<VideoModel, VideosFireBaseAdapter.MyHolder> {

    private boolean isFav = false;

    public VideosFireBaseAdapter(@NonNull FirebaseRecyclerOptions<VideoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull VideoModel model) {
        holder.textVideoTitle.setText(model.getTitle());
        holder.textVideoDescription.setText(model.getDesc());
        holder.textLike.setText(String.valueOf(model.getLikeCount()));
        holder.videoView.setVideoURI(Uri.parse(model.getUrl()));
        holder.videoView.setOnPreparedListener(mp -> {
            Log.d("VideoStatus", "Video is prepared");
            holder.videoProgressBar.setVisibility(View.GONE);
            mp.start();
            float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
            float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
            float scale = videoRatio / screenRatio;
            if (scale >= 1f){
                holder.videoView.setScaleX(scale);
            }else {
                holder.videoView.setScaleY(1f/scale);
            }
        });

        // Lấy thông tin người upload
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users").child(model.getUserId());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                if (user != null) {
                    holder.textName.setText(user.getName());
                    holder.textEmail.setText(user.getEmail());

                    if (user.getAvt() != null && !user.getAvt().isEmpty()) {
                        Glide.with(holder.itemView.getContext())
                                .load(user.getAvt())
                                .into(holder.imPerson);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Lỗi khi lấy user info: " + error.getMessage());
            }
        });

        holder.videoView.setOnCompletionListener(MediaPlayer::start);
        String videoKey = getRef(position).getKey();
        holder.favorites.setOnClickListener(v -> {
            DatabaseReference videoRef = FirebaseDatabase.getInstance("https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("videos").child(videoKey);

            isFav = !isFav;
            int newLikeCount;
            if (isFav) {
                holder.favorites.setImageResource(R.drawable.ic_favorite_active);
                newLikeCount = model.getLikeCount() + 1;
            } else {
                holder.favorites.setImageResource(R.drawable.ic_favorite);
                newLikeCount = model.getLikeCount() - 1;
            }

            videoRef.child("likeCount").setValue(newLikeCount);
            holder.textLike.setText(String.valueOf(newLikeCount));
        });
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return new MyHolder(view);
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private final VideoView videoView;
        private final ProgressBar videoProgressBar;
        private final TextView textVideoTitle, textVideoDescription, textName, textEmail, textLike;
        private final ImageView imPerson, favorites;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoProgressBar = itemView.findViewById(R.id.videoProgressBar);
            textVideoTitle = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            textName = itemView.findViewById(R.id.tv_name);
            textEmail = itemView.findViewById(R.id.tv_email);
            textLike = itemView.findViewById(R.id.likeCount);
            imPerson = itemView.findViewById(R.id.imPerson);
            favorites = itemView.findViewById(R.id.favorites);
        }
    }
}

