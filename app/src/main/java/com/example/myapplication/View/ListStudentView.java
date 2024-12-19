package com.example.myapplication.View;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.AdapterStudent;
import com.example.myapplication.Class.Student;
import com.example.myapplication.Class.lessonSelection;
import com.example.myapplication.Class.lessonSelection;
import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListStudentView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView btnReturn;
    private  String TAG ="ListStudent";
    private TextView textView18;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_student_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận danh sách sinh viên từ Intent
        List<Student> studentList = (List<Student>) getIntent().getSerializableExtra("lstStudent");
        if (studentList == null) {
            studentList = new ArrayList<>(); // Khởi tạo danh sách rỗng nếu không có dữ liệu
        }

        recyclerView = findViewById(R.id.recyclerStudent);
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v -> finish());
        textView18 = findViewById(R.id.textView18);

        textView18.setText("Số lượng: ("+ String.valueOf(studentList.size())+ ")");
        // Thiết lập Adapter và RecyclerView
        AdapterStudent adapterStudent = new AdapterStudent(studentList);
        recyclerView.setAdapter(adapterStudent);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

}