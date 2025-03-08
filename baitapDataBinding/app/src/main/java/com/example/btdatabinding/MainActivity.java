package com.example.btdatabinding;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.btdatabinding.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListUserAdapter.OnItemClickListener {

    public ObservableField<String> title = new ObservableField<>();
    private ListUserAdapter listUserAdapter;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        title.set("Ví dụ data binding cho recycler view");
        binding.setHome(this);
        setData();
        binding.rcView.setLayoutManager(new LinearLayoutManager(this));
        binding.rcView.setAdapter(listUserAdapter);

    }

    private void setData(){
        List<User> userList = new ArrayList<>();
        for (int i = 0; i< 10; i++){
            User user = new User("Đức " + i, "Phát" + i);
            userList.add(user);
        }
        listUserAdapter = new ListUserAdapter(userList);
        listUserAdapter.setOnItemClickListener(this);
    }

    @Override
    public void itemClick(User user){
        Toast.makeText(this, "bạn vừa click", Toast.LENGTH_SHORT).show();
    }
}