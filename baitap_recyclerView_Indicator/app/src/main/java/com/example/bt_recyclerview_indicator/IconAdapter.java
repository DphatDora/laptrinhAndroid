package com.example.bt_recyclerview_indicator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder>{

    private Context context;
    private List<IconModel> iconModelList;

    public IconAdapter(Context context, List<IconModel> iconModelList) {
        this.context = context;
        this.iconModelList = iconModelList;
    }

    @NonNull
    @Override
    public IconAdapter.IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_icon_promotion, parent, false);
        return new IconHolder(view);
    }

    @Override
    public void onBindViewHolder(IconHolder holder, int position) {
        IconModel iconModel = iconModelList.get(position);
        Glide.with(context).load(iconModel.getImdId()).into(holder.ivImgIcon);
        holder.tvIcon.setText(iconModel.getDesc());
    }

    @Override
    public int getItemCount() {
        if(iconModelList != null){
            return iconModelList.size();
        }
        return 0;
    }

    public class IconHolder extends RecyclerView.ViewHolder{
        private ImageView ivImgIcon;
        private TextView tvIcon;

        public IconHolder(@NonNull View itemView) {
            super(itemView);
            ivImgIcon = itemView.findViewById(R.id.ivImgIcon);
            tvIcon = itemView.findViewById(R.id.tvIcon);
        }
    }

    public void setListenerList(List<IconModel> iconModelList){
        this.iconModelList = iconModelList;
        notifyDataSetChanged();
    }
}
