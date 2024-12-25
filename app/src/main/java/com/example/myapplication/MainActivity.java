package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.View.signin;
import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton btnLetStarted;

    public List<String> lstID, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo danh sách
        lstID = new ArrayList<>();
        lastName = new ArrayList<>();

        // Đọc tệp và xử lý dữ liệu


        // Thiết lập sự kiện cho nút bấm
        btnLetStarted = findViewById(R.id.btnLetStarted);
        btnLetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(this, signin.class);
            startActivity(intent);
        });

        if(FirebaseApp.getInstance() !=null){
            Log.e("Install Firebase","True");
        }
        else {
            Log.e("Install Firebase","False");
        }
    }


}
