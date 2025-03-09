package com.example.bt_sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btnTxt;
    EditText usernameTxt, passwordTxt;
    CheckBox cbRemember;
    SharedPreferences sharedPreferences;

    private void mappingElement(){
        btnTxt = findViewById(R.id.buttonTxt);
        usernameTxt = findViewById(R.id.userNameTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        cbRemember = findViewById(R.id.cbmemberme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mappingElement();

        btnTxt.setOnClickListener(view -> {
            String username = usernameTxt.getText().toString().trim();
            String password = passwordTxt.getText().toString().trim();
            if (username.equals("admin") && password.equals("admin")){
                Toast.makeText(HomeActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                if (cbRemember.isChecked()){
                    // chỉnh sửa nội dung file lưu trữ
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("taikhoan", username);
                    editor.putString("matkhau", password);
                    editor.putBoolean("trangthai", true);
                    editor.commit();
                }
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("taikhoan");
                    editor.remove("matkhau");
                    editor.remove("trangthai");
                    editor.commit();
                }
            }
            else{
                Toast.makeText(HomeActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        // khởi tạo sharePreference
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        usernameTxt.setText(sharedPreferences.getString("taikhoan",""));
        passwordTxt.setText(sharedPreferences.getString("matkhau",""));
        cbRemember.setChecked(sharedPreferences.getBoolean("trangthia",false));
    }
}
