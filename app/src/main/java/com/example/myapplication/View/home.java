package com.example.myapplication.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Class.AdapterLesson;
import com.example.myapplication.Class.Lesson;
import com.example.myapplication.Class.LessonManager;
import com.example.myapplication.Class.User;
import com.example.myapplication.Class.UserManager;
import com.example.myapplication.Class.lessonSelection;
import com.example.myapplication.DAO.DAO;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    private RecyclerView recyclerViewLesson;
    private lessonSelection lessonSelection;
    private TextView textView9,txtMGV;
    private ImageView avtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewLesson = findViewById(R.id.recyclerViewLesson);
        textView9 = findViewById(R.id.textView9);
        txtMGV = findViewById(R.id.txtMGV);
        avtUrl = findViewById(R.id.avtUrl);

        List<Lesson> lessonList = new ArrayList<Lesson>();

//        lessonList.add(new Lesson("Lập trình Ứng dụng","10 AM - 12 AM | 2 hr 0m","507_2-A3","MUL1448-20241",1));
//        lessonList.add(new Lesson("Bản quyền số","10 AM - 12 AM | 2 hr 0m","507_2-A3","MUL1448-20241",2));
//        lessonList.add(new Lesson("Khai phá dữ liệu","10 AM - 12 AM | 2 hr 0m","507_2-A3","MUL1448-20241",3));
//        lessonList.add(new Lesson("Thị giác máy tính","10 AM - 12 AM | 2 hr 0m","507_2-A3","MUL1448-20241",4));
        User user = UserManager.getInstance().getUser();
        textView9.setText(user.getName());
        txtMGV.setText("Mã: "+ user.getId());
        String userId = UserManager.getInstance().getUser().getId();
        Log.d("Login", "tmpUserID: " + userId);
        String tmpavtUrl = "https://www.google.com/search?q=%E1%BA%A3nh+png&sca_esv=32e55b09f5ce6c02&sxsrf=ADLYWIJYTKfhLvRW09ygZOsrRDhO7FvkMQ%3A1734455394114&ei=YrBhZ8jTBqjT2roP5Zjz6Ao&oq=%E1%BA%A3nh+&gs_lp=Egxnd3Mtd2l6LXNlcnAiBuG6o25oICoCCAAyChAjGIAEGCcYigUyBBAjGCcyBBAjGCcyBRAAGIAEMgoQABiABBhDGIoFMg4QABiABBixAxiDARiKBTIFEAAYgAQyBRAAGIAEMggQABiABBixAzIFEAAYgARIpQxQxgFYxgFwAXgBkAEAmAF0oAF0qgEDMC4xuAEDyAEA-AEBmAICoAJ8wgIHECMYsAMYJ8ICChAAGLADGNYEGEeYAwCIBgGQBgqSBwMxLjGgB8cI&sclient=gws-wiz-serp#vhid=kUnHzPp0QpPvYM&vssid=_urBhZ8OoEunn2roP7_W-QA_38";
        Glide.with(this).load(tmpavtUrl).into(avtUrl);
        DAO dao = new DAO(this);
        dao.getLessonManagers(userId, new DAO.LessonCallback() {
            @Override
            public void onResult(List<LessonManager> lessonManagers) {
                if (lessonManagers != null) {
                    // Xóa dữ liệu cũ trong lessonList
                    lessonList.clear();

                    // Thêm dữ liệu mới vào lessonList

                    for (LessonManager lessonManager : lessonManagers) {
                        Log.e("Login",lessonManager.toString());
                        Lesson lesson = new Lesson(
                                lessonManager.getName(),
                                lessonManager.getTime(),
                                lessonManager.getLocation(),
                                lessonManager.getCredit(),
                                lessonManager.getId()
                        );
                        Log.e("Login",lesson.toString());
                        lessonList.add(lesson);
                    }
                    AdapterLesson apdapterLesson = new AdapterLesson(lessonList,home.this,lessonSelection);
                    recyclerViewLesson.setAdapter(apdapterLesson);
                    recyclerViewLesson.setLayoutManager(new GridLayoutManager(home.this,1));

                    // Thông báo cho Adapter biết dữ liệu đã thay đổi
//                    adapterLesson.notifyDataSetChanged();
                } else {
                    Log.e("Lesson", "Không lấy được danh sách bài học.");
                }
            }
        });



    }
}