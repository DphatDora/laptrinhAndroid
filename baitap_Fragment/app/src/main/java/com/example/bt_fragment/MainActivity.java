package com.example.bt_fragment;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bt_fragment.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ViewPager2Adapter viewPager2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //tool bar
        setSupportActionBar(binding.toolBar);

        FloatingActionButton fabAction = binding.fabAction;
        fabAction.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        });

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Xác nhận"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Lấy hàng"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đang giao"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Đánh giá"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Hủy"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager2Adapter = new ViewPager2Adapter(fragmentManager, getLifecycle());
        binding.viewPager2.setAdapter(viewPager2Adapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager2.setCurrentItem(tab.getPosition());
                changeFabIcon(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }

    private void changeFabIcon(final int index){
        binding.fabAction.hide();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                switch (index){
                    case 0:
                        binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
                        break;
                    case 1:
                        binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
                        break;
                    case 2:
                        binding.fabAction.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
                        break;

                }
                binding.fabAction.show();
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuSearch) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuNewGroup) {
            Toast.makeText(this, "New Group", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuBroadcast) {
            Toast.makeText(this, "Broadcast", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuWeb) {
            Toast.makeText(this, "Web", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuMessage) {
            Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuSetting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}