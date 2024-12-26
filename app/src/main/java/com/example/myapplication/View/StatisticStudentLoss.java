package com.example.myapplication.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.AdapterStudentLoss;
import com.example.myapplication.Class.StudentLoss;
import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StatisticStudentLoss extends AppCompatActivity {
    private String user_id,lesson_id;
    private String TAG = "StatisticStudentLossTEST";
    private List<StudentLoss>lstStuLoss;
    private List<String> lstID;
    private List<String>lstCnt;
    private RecyclerView recyclerStatisticStudent;
    private ImageView btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistic_student_loss);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerStatisticStudent = findViewById(R.id.recyclerStatisticStudent);
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v->{
            finish();
        });

        lstID = new ArrayList<>();
        lstCnt = new ArrayList<>();
        user_id = (String)getIntent().getSerializableExtra("userID");
        lesson_id = (String)getIntent().getSerializableExtra("lessonID");
        lstStuLoss = new ArrayList<>();

        DAO dao = new DAO(this);
        dao.getListStatisticStudentLoss(lesson_id, user_id, new DAO.StatisticStudentLossCalback() {
            @Override
            public void onResult(List<String> lstStatisticStLoss) {
                for(int i=0;i<lstStatisticStLoss.size();i++){
                    Log.d(TAG,lstStatisticStLoss.get(i));
                    String id ="";
                    String cnt="";
                    for(int j=15;j<=24;j++){
                        id+= lstStatisticStLoss.get(i).charAt(j);
                    }
                    for(int j=lstStatisticStLoss.get(i).length()-2;j>lstStatisticStLoss.get(i).length()-3;j--){
                        cnt+= lstStatisticStLoss.get(i).charAt(j);
                    }
                    lstID.add(id);
                    lstCnt.add(cnt);
                }
                for(String st:lstID){
                    Log.d(TAG,st);
                }
                for(String st:lstCnt){
                    Log.d(TAG,st);
                }
                for(int i=0;i<lstID.size();i++){
                    StudentLoss stloss = new StudentLoss();
                    stloss.setId(lstID.get(i));
                    stloss.setCount(lstCnt.get(i));
                    lstStuLoss.add(stloss);
                }

                for(StudentLoss st: lstStuLoss){
                    Log.d(TAG,st.toString());
                }

                AdapterStudentLoss adapterStudentLoss = new AdapterStudentLoss(lstStuLoss);
                recyclerStatisticStudent.setAdapter(adapterStudentLoss);
                recyclerStatisticStudent.setLayoutManager(new GridLayoutManager(StatisticStudentLoss.this, 1));

            }
        });


    }
}