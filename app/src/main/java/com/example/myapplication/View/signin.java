package com.example.myapplication.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.myapplication.Class.User;
import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;

import java.sql.Connection;

public class signin extends AppCompatActivity {
    private TextView txtSignUp;
    private EditText txtEmail, txtPassword;
    private  String isConnect;

    private AppCompatButton appCompatButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtSignUp = findViewById(R.id.txtSignUp);
        txtPassword = findViewById(R.id.txtPassword);
        txtEmail = findViewById(R.id.txtEmail);

        appCompatButton = findViewById(R.id.appCompatButton);

        txtSignUp.setOnClickListener(v ->{
            Intent intent = new Intent(signin.this,signup.class);
            startActivity(intent);
        });
        appCompatButton.setOnClickListener(v ->{
            String tmpEmail = String.valueOf(txtEmail.getText());
            String passowrd = String.valueOf(txtPassword.getText());
            DAO dao = new DAO(signin.this);
            dao.login(tmpEmail, passowrd, isConnectResult -> {
                Log.e("Login", isConnectResult);
                if ("true".equals(isConnectResult)) {
                    // Điều hướng sang Activity khác nếu cần
                    Intent intent = new Intent(signin.this, home.class);
                    startActivity(intent);
                }
            });
        });


    }


}