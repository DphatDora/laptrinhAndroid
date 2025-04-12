package com.example.bt_firebasevideo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText emailInput, passwordInput;
    Button signupBtn;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://video-short-fe473-default-rtdb.asia-southeast1.firebasedatabase.app/");


        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        signupBtn = findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();

                        String name = email.split("@")[0];
                        String avatar = "data:image/png;base64,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";

                        UserModel user = new UserModel(email, password, name, avatar);

                        // Lưu user vào Realtime Database
                        database.getReference("users")
                                .child(uid)
                                .setValue(user)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Đăng ký thành công và đã lưu user!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, LoginActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Lỗi lưu dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
