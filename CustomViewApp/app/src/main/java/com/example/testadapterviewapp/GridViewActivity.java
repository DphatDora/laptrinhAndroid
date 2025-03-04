package com.example.testadapterviewapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    //khai bao
    GridView gridview;
    ArrayList<MonHoc> arrayList;
    MonhocAdapter adapter;
    EditText editText;
    Button btnNhap;
    Button btnCapNhat;
    int vitri = -1;
    private void AnhXa() {
        gridview = findViewById(R.id.gridview);
        editText =  findViewById(R.id.editText);
        btnNhap =  findViewById(R.id.btnNhap);
        btnCapNhat =  findViewById(R.id.btnCapNhap);
        //Thêm dữ liệu vào List
        arrayList = new ArrayList<>();
        arrayList.add(new MonHoc("Java","Java 1",R.drawable.java1));
        arrayList.add(new MonHoc("C#","C# 1",R.drawable.c));
        arrayList.add(new MonHoc("PHP","PHP 1",R.drawable.php));
        arrayList.add(new MonHoc("Kotlin","Kotlin 1",R.drawable.kotlin));
        arrayList.add(new MonHoc("Dart","Dart 1",R.drawable.dart));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.custom_grid_view);
        //anh xa
        AnhXa();

        //tao adapter
        adapter = new MonhocAdapter(GridViewActivity.this, R.layout.grid_monhoc, arrayList);

        //truyen data tu adapter ra listview
        gridview.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.custom_gridview), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnNhap.setOnClickListener(view -> {
            String name = editText.getText().toString();
            arrayList.add(new MonHoc(name,"Java 1",R.drawable.java1));
            adapter.notifyDataSetChanged();
        });

        //bắt sự kiện trên từng dòng của Listview
        gridview.setOnItemClickListener((adapterView, view, i, l) -> {
            //lấy nội dung đua lên edittext
            vitri = i;
            editText.setText(arrayList.get(i).getName());
        });

        btnCapNhat.setOnClickListener(view -> {
            if (vitri != -1) { // Kiểm tra có chọn item hay không
                arrayList.get(vitri).setName(editText.getText().toString().trim());
                adapter.notifyDataSetChanged();
                editText.setText(""); // Xóa EditText sau khi cập nhật
                vitri = -1; // Reset lại vị trí đã chọn
            }
        });

    }
}