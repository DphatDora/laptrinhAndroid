package com.example.bt_recyclerview_indicator;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IconAdapter adapter;
    private List<IconModel> arrayList;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcIcon);

        arrayList = new ArrayList<>();
        arrayList.add(new IconModel(R.drawable.ic_arrow_right, "dora chan"));
        arrayList.add(new IconModel(R.drawable.ic_back, "nobita"));
        arrayList.add(new IconModel(R.drawable.ic_close, "furina"));
        arrayList.add(new IconModel(R.drawable.ic_delete, "furina"));
        arrayList.add(new IconModel(R.drawable.ic_discount, "mavuika"));
        arrayList.add(new IconModel(R.drawable.ic_dropdown_arrow, "kinich"));
        arrayList.add(new IconModel(R.drawable.ic_edit_avatar, "mualani"));
        arrayList.add(new IconModel(R.drawable.ic_extra_card, "nevillette"));
        arrayList.add(new IconModel(R.drawable.ic_food, "nahida"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new IconAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        // Thêm indicator
        recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterListener(s);
                return false;
            }
        });
    }

    private void filterListener(String text){
        List<IconModel> filterList = new ArrayList<>();
        for(IconModel item : arrayList){
            if(item.getDesc().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this, " no data found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setListenerList(filterList);
        }
    }

}
