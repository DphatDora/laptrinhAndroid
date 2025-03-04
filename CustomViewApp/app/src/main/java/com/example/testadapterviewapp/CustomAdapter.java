package com.example.testadapterviewapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Object> mObjects;
    public static final int TEXT = 0;
    public static final int IMAGE = 1;
    public static final int USER = 2;

    public CustomAdapter(Context mContext, List<Object> mObjects) {
        this.mContext = mContext;
        this.mObjects = mObjects;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView tvText;
        public TextViewHolder(View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    Toast.makeText(mContext, mObjects.get(getAbsoluteAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        private ImageView imvImage;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imvImage = itemView.findViewById(R.id.imv_image);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(mContext, mObjects.get(getAbsoluteAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvAddress;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    UserModel user = (UserModel) mObjects.get(getAbsoluteAdapterPosition());
                    Toast.makeText(mContext, user.getName() + ", " + user.getAddress() + ", ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //tạo viewHolder tương ứng
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        switch (viewType){
            case TEXT:
                View itemView0 = li.inflate(R.layout.row_text, parent, false);
                return new TextViewHolder(itemView0);
            case IMAGE:
                View itemView1 = li.inflate(R.layout.row_image, parent, false);
                return new ImageViewHolder(itemView1);
            case USER:
                View itemView2 = li.inflate(R.layout.row_user, parent, false);
                return new UserViewHolder(itemView2);
            default:
                break;

        }
        return null;
    }

    //gán dữ liệu viewHolder tương ứng
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case TEXT:
                TextViewHolder textViewHolder  = (TextViewHolder) holder;
                textViewHolder.tvText.setText(mObjects.get(position).toString());
                break;
            case IMAGE:
                ImageViewHolder imageViewHolder  = (ImageViewHolder) holder;
                imageViewHolder.imvImage.setImageResource((int) mObjects.get(position));
                break;
            case USER:
                UserModel user = (UserModel) mObjects.get(position);
                UserViewHolder userViewHolder  = (UserViewHolder) holder;
                userViewHolder.tvName.setText(user.getName());
                userViewHolder.tvAddress.setText(user.getAddress());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    @Override
    public int getItemViewType(int position){
        if (mObjects.get(position) instanceof String){
            return TEXT;
        }
        else if (mObjects.get(position) instanceof Integer){
            return IMAGE;
        }
        else if (mObjects.get(position) instanceof UserModel){
            return USER;
        }
        return -1;
    }

}
