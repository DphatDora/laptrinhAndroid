package com.example.baitap1;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        //set background khi load app
        ConstraintLayout bg= (ConstraintLayout) findViewById(R.id.main);
        bg.setBackgroundColor(Color.CYAN);
        //bg.setBackgroundResource(R.drawable.bg_image);


        // tao mang
        Button btn_array = findViewById(R.id.arrayBtn);
        btn_array.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> randomArray = generateRandomArray(10, 0, 20);

                ArrayList<Integer> evenNumbers = new ArrayList<>();
                ArrayList<Integer> oddNumbers = new ArrayList<>();
                for (int num : randomArray) {
                    if (num % 2 == 0) {
                        evenNumbers.add(num);
                    } else {
                        oddNumbers.add(num);
                    }
                }

                Log.d("RandomArray", "Array: " + randomArray);
                Log.d("RandomArray", "So chan: " + evenNumbers);
                Log.d("RandomArray", "So le: " + oddNumbers);
            }
        });

        //Đảo chuỗi
        EditText editText = findViewById(R.id.editTextText);
        TextView textView = findViewById(R.id.textView2);
        Button btnString = findViewById(R.id.strBtn);

        btnString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                String outputText = nghichDaoChuoi(inputText);
                textView.setText(outputText);
                Toast.makeText(MainActivity.this, "Chuỗi đảo: " + outputText, Toast.LENGTH_SHORT).show();
            }
        });

        //change background khi click switch
        Switch sw = (Switch) findViewById(R.id.switch_bg);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bg.setBackgroundResource(R.drawable.bg_image);
                }else{
                    bg.setBackgroundColor(Color.CYAN);
                }
            }
        });
    }

    private ArrayList<Integer> generateRandomArray(int size, int min, int max) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arrayList.add(random.nextInt(max - min) + min);
        }
        return arrayList;
    }

    private String nghichDaoChuoi(String input) {
        String[] words = input.split(" ");
        StringBuilder chuoiNghichDao = new StringBuilder();
        for (int i= words.length-1;i>=0;i--){
            chuoiNghichDao.append(words[i]);
            if (i>0){
                chuoiNghichDao.append(" ");
            }
        }
        return chuoiNghichDao.toString().toUpperCase();
    }
}