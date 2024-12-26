package com.example.myapplication.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.AdapterDraftCheckin;
import com.example.myapplication.Class.UserManager;
import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryCheckin extends AppCompatActivity {
    private TextView txtLessonID,txtCountDraft;
    private RecyclerView recyclerDraftCheckin;
    private ImageView btnReturn;
    private List<String> lstDraftCheckinDone;
    private String lesson_id,userId,timeStamp;
    private String TAG = "HistoryCheckinTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_checkin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        txtLessonID = findViewById(R.id.txtLessonID);
        txtCountDraft = findViewById(R.id.txtCountDraft);
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(v ->{
            finish();
        });

        recyclerDraftCheckin = findViewById(R.id.recyclerDraftCheckin);

        lesson_id = (String)getIntent().getSerializableExtra("lessonID");
        userId = UserManager.getInstance().getUser().getId();
        timeStamp = (String)getIntent().getSerializableExtra("timeStamp");

        txtLessonID.setText(lesson_id);

        lstDraftCheckinDone = new ArrayList<>();
        getDraftCheckin();
    }

    public void getDraftCheckin(){
        DAO dao = new DAO(this);

        dao.getDraftCheckin(lesson_id, userId, new DAO.GetDraftCheckinCallback() {
            @Override
            public void onResult(List<String> lstDraftCheckin) {
                if (lstDraftCheckin != null){

                    for (String st: lstDraftCheckin){
                        String tmp="";
                        for(int i=14;i<st.length()-2;i++){
                             tmp += st.charAt(i) ;
                        }
                        lstDraftCheckinDone .add(tmp);

                        Log.d(TAG,"lstDraftCheckin: "+ st);
                    }

                    AdapterDraftCheckin adapterDraftCheckin = new AdapterDraftCheckin(lstDraftCheckinDone);
                    recyclerDraftCheckin.setAdapter(adapterDraftCheckin);
                    recyclerDraftCheckin.setLayoutManager(new GridLayoutManager(HistoryCheckin.this, 1));
                }
            }
        });
    }


}