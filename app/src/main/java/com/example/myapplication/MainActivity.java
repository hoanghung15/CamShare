package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.View.signin;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.*;
import org.opencv.imgproc.Imgproc;


import java.util.Random;




public class MainActivity extends AppCompatActivity {
    private AppCompatButton btnLetStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLetStarted =findViewById(R.id.btnLetStarted);
        btnLetStarted.setOnClickListener(v ->{
            Intent intent = new Intent(this, signin.class);
            startActivity(intent);
        });
    }


}