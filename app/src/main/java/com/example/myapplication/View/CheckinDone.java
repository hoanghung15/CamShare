package com.example.myapplication.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Class.AdapterStudent;
import com.example.myapplication.Class.Student;
import com.example.myapplication.Class.UserManager;
import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckinDone extends AppCompatActivity {
    private List<Student> lstStudent;
    private List<String>lstIdDone;
    private List<String>lstIdLoss;
    private String TAG ="CheckinDoneTest";
    private Set<Student> lstStCheckin,lstStLoss;
    private String timeStamp,lessonName,lesssonID,date,time;
    private TextView txtDateTime,txtCountCheckin,txtLoss;

    private List<Student>lstStCheckinDone = new ArrayList<>();
    private List<Student>lstStCheckinLoss = new ArrayList<>();
    private List<String>lstDraft;

    private RecyclerView recyclerStudentLoss,recyclerStudentCheckin;
    private ImageView btnReturn;
    private AppCompatButton btnSavenDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkin_done);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtCountCheckin = findViewById(R.id.txtCountCheckin);
        btnSavenDownload = findViewById(R.id.btnSavenDownload);
        txtLoss = findViewById(R.id.txtLoss);
        txtDateTime = findViewById(R.id.txtDateTime);
        recyclerStudentLoss =findViewById(R.id.recyclerStudentLoss);
        recyclerStudentCheckin = findViewById(R.id.recyclerStudentCheckin);
        btnReturn = findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(v ->{
            finish();
        });
        btnSavenDownload.setOnClickListener(v ->{
            String userId = UserManager.getInstance().getUser().getId();
            try {
                saveFileToPublicDownloads();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,e.toString());
                Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
            }

            updateStudentLoss();

            Intent intent = new Intent(this, HistoryCheckin.class);
            intent.putExtra("lessonID",lesssonID);
            intent.putExtra("userID",  userId);
            intent.putExtra("timeStamp",timeStamp);
            startActivity(intent);
        });

        lstStudent =(List<Student>) getIntent().getSerializableExtra("lstStudent");
        lstIdDone =(List<String>)getIntent().getSerializableExtra("lstIdDone");
        lstIdLoss = new ArrayList<>();
        lstStCheckin = new HashSet<>();
        lstDraft = new ArrayList<>();

        timeStamp = (String)getIntent().getSerializableExtra("timeStamp");
        lessonName = (String)getIntent().getSerializableExtra("lessName");
        lesssonID = (String)getIntent().getSerializableExtra("lessID");
        date=(String)getIntent().getSerializableExtra("date");
        time=(String)getIntent().getSerializableExtra("time");
        Log.e(TAG,lessonName+lesssonID);
        txtDateTime.setText(timeStamp);

        Log.e(TAG,timeStamp);
        lstStLoss = new HashSet<>();
        Log.e(TAG,String.valueOf(lstStudent.size()));
        Log.e(TAG,String.valueOf(lstIdDone.size()));

        sortStudentCheckin();
        convertList();

        txtLoss.setText(String.valueOf("SL vắng: "+lstStCheckinLoss.size()));
        txtCountCheckin.setText(String.valueOf("SL đi học: "+lstStCheckinDone.size()));

        AdapterStudent adapterStudent = new AdapterStudent(lstStCheckinLoss);
        recyclerStudentLoss.setAdapter(adapterStudent);
        recyclerStudentLoss.setLayoutManager(new GridLayoutManager(this, 1));

        AdapterStudent adapterStudent1 = new AdapterStudent(lstStCheckinDone);
        recyclerStudentCheckin.setAdapter(adapterStudent1);
        recyclerStudentCheckin.setLayoutManager(new GridLayoutManager(this, 1));

    }

    public  void sortStudentCheckin(){
        for(String stID :lstIdDone){
            Log.e(TAG,stID);
            for(Student st : lstStudent){
                Log.e(TAG,st.toString());
                if (stID.equals(st.getId())){
                    Log.e(TAG,stID);
                    lstStCheckin.add(st);
                }
                else{
                    lstStLoss.add(st);
                }
            }
        }

        Log.e(TAG,String.valueOf(lstStCheckin.size()));
        Log.e(TAG,String.valueOf(lstStLoss.size()));

    }
    public void convertList(){
        for(Student st : lstStCheckin){
            lstStCheckinDone.add(st);
        }
        for(Student st : lstStLoss){
            lstStCheckinLoss.add(st);
            lstIdLoss.add(st.getId());
        }
        Log.d(TAG,String.valueOf(lstStCheckinDone.size()) +" "+String.valueOf(lstStCheckinLoss.size()) );
    }

    private void saveFileToPublicDownloads() throws IOException {
        // Tên file
        String fileName = lesssonID+"_"+date+".txt";

        // Đường dẫn đến thư mục Download công khai
        File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!downloadsDirectory.exists()) {
            downloadsDirectory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Tạo đối tượng tệp trong thư mục Download
        File destinationFile = new File(downloadsDirectory, fileName);

        // Kiểm tra và xử lý nếu tệp đã tồn tại
        if (destinationFile.exists()) {
            Toast.makeText(this, "File already exists. Overwriting...", Toast.LENGTH_SHORT).show();
            // Nếu muốn giữ lại file cũ, hãy dừng hàm tại đây
            // return;
        }

        // Tạo nội dung file
        StringBuilder content = new StringBuilder();

        // Ghi dữ liệu lstStCheckinLoss
        content.append("lstStCheckinLoss:\n");
        content.append(lstStCheckinLoss.size()).append("\n");
        for (Student student : lstStCheckinLoss) {
            content.append(student.getName()).append(":").append(student.getId()).append("\n");
        }

        // Ghi dữ liệu lstStCheckinDone
        content.append("lstStCheckinDone:\n");
        content.append(lstStCheckinDone.size()).append("\n");
        for (Student student : lstStCheckinDone) {
            content.append(student.getName()).append(":").append(student.getId()).append("\n");
        }

        // Lưu nội dung vào tệp
        try (FileOutputStream fos = new FileOutputStream(destinationFile, false)) { // `false` để ghi đè
            fos.write(content.toString().getBytes());
        }

        Toast.makeText(this, "File saved to Downloads: " + destinationFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
    }

    public void updateStudentLoss(){
        String userId = UserManager.getInstance().getUser().getId();
        Log.d(TAG,"userID: "+userId);
        Log.d(TAG,"lessonID: "+lesssonID);
        Log.d(TAG,"list:"+String.valueOf(lstIdLoss));
        Log.d(TAG,"time: "+timeStamp);

        for(String st:lstIdLoss){
            DAO dao = new DAO(this);
            dao.updateStudentLoss(userId,lesssonID,st,timeStamp);
        }

    }


}