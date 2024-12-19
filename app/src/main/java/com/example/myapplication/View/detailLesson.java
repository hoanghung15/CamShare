package com.example.myapplication.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Class.Student;
import com.example.myapplication.Class.StudentManager;
import com.example.myapplication.Class.lessonSelection;

import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class detailLesson extends AppCompatActivity {
    private ImageView btnReturn;
    private TextView txtIdLesson;
    private TextView intCredit;
    private  String test;
    private  TextView txtNameClass;
    private ProgressBar prgProcess;
    private AppCompatButton btndiemdanh;
    private FrameLayout frmDSSV;
    private  lessonSelection lessonSelection;
    public List<Student>lstStudent = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_lesson);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        intCredit = findViewById(R.id.intCredit);
        txtIdLesson = findViewById(R.id.txtIdLesson);
        txtNameClass = findViewById(R.id.txtNameClass);
        prgProcess =findViewById(R.id.prgProcess);
        btndiemdanh = findViewById(R.id.btndiemdanh);
        frmDSSV = findViewById(R.id.frmDSSV);

        frmDSSV.setOnClickListener(v->{
            Intent intent = new Intent(detailLesson.this, ListStudentView.class);
            intent.putExtra("lstStudent", new ArrayList<>(lstStudent));
            startActivity(intent);
        });

        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v->{
            finish();
        });
        lessonSelection = (lessonSelection) getIntent().getSerializableExtra("lessonSelection");
//        intCredit.setText(String.valueOf(lessonSelection.getCredit()));
        test = String.valueOf(lessonSelection.getCredit());
        Log.d("aaaaaa", test);
//       Logic
        txtNameClass.setText(lessonSelection.getName());
        txtIdLesson.setText(lessonSelection.getId());
        intCredit.setText(String.valueOf(lessonSelection.getCredit())+" tín chỉ");
        prgProcess.setProgress(100);

        btndiemdanh.setOnClickListener(v ->{
            Intent intent = new Intent(detailLesson.this, CameraCheckin.class);
            startActivity(intent);
        });
        String lesson_id = lessonSelection.getId();
        Log.e("tmpID1",lesson_id);
        DAO dao = new DAO(this);

        dao.getStudentManagers(lesson_id, new DAO.StudentCallback() {
            @Override
            public void onResult(List<StudentManager> studentManagers) {
                if(studentManagers != null){
                    for(StudentManager student: studentManagers){
                        lstStudent.add(student);
                        Log.e("tmpID",student.toString());
                    }
                }
            }
        });


    }
}