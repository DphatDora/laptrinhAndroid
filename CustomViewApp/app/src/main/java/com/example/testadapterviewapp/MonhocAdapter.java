package com.example.testadapterviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MonhocAdapter extends BaseAdapter {

    private class ViewHolder{
        TextView textName,textDesc;
        ImageView imagePic;
    }

    private Context context;
    private int layout;
    private List<MonHoc> monhocList;

    public MonhocAdapter(Context context, int layout, List<MonHoc> monhocList) {
        this.context = context;
        this.layout = layout;
        this.monhocList = monhocList;
    }

    @Override
    public int getCount() {
        return monhocList.size();
    }

    @Override
    public Object getItem(int i) {
        return monhocList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //khởi tạo viewholder
        ViewHolder viewHolder;

        //lấy context
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //gọi view chứa layout
            view = inflater.inflate(layout,null);
            //ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.textName = view.findViewById(R.id.textName);
            viewHolder.textDesc = view.findViewById(R.id.textDesc);
            viewHolder.imagePic = view.findViewById(R.id.imagePic);

            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        //gán giá trị
        MonHoc monHoc = monhocList.get(i);
        viewHolder.textName.setText(monHoc.getName());
        viewHolder.textDesc.setText(monHoc.getDesc());
        viewHolder.imagePic.setImageResource(monHoc.getPic());

        //trả về view
        return view;
    }
}

