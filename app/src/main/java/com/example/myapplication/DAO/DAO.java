package com.example.myapplication.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Class.LessonManager;
import com.example.myapplication.Class.StudentManager;
import com.example.myapplication.Class.User;
import com.example.myapplication.Class.UserManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO {
    private String email, password;
    private Context context;
    private static final String TAG = "DAO";
    private static final String LOGIN_URL = "http://192.168.1.32/CamCheckin/login.php";
    private static final String LESSONS_URL = "http://192.168.1.32/CamCheckin/getLesson.php";
    private static final String STUDENTS_URL = "http://192.168.1.32/CamCheckin/fetch_sinhvien.php";

    // Constructor nhận Context
    public DAO(Context context) {
        this.context = context;
    }

    public DAO(String password, String email, Context context) {
        this.password = password;
        this.email = email;
        this.context = context;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Phương thức login với callback
    public void login(final String email, final String password, final LoginCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String isConnect = "false";
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                JSONObject userData = jsonResponse.getJSONObject("data");
                                isConnect = "true";
                                // Tạo đối tượng User và lưu vào UserManager
                                User user = new User();
                                user.setId(userData.getString("id"));
                                user.setEmail(userData.getString("email"));
                                user.setName(userData.getString("name"));
                                user.setAvtUrl(userData.getString("avtUrl"));
                                user.setRole(userData.getString("role"));

                                // Lưu User vào UserManager
                                UserManager.getInstance().setUser(user);

                                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                String message = jsonResponse.optString("message", "Lỗi đăng nhập");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Lỗi phân tích JSON: " + e.getMessage());
                        }
                        // Truyền giá trị qua callback
                        callback.onResult(isConnect);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Lỗi khi gọi API: " + error.getMessage());
                        Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                        // Truyền giá trị qua callback khi lỗi
                        callback.onResult("false");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    // Phương thức lấy danh sách môn học
    public void getLessonManagers(final String userId, final LessonCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LESSONS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                JSONArray lessonsArray = jsonResponse.getJSONArray("lessons");
                                List<LessonManager> lessonManagers = new ArrayList<>();

                                for (int i = 0; i < lessonsArray.length(); i++) {
                                    JSONObject lessonObj = lessonsArray.getJSONObject(i);
                                    LessonManager lesson = new LessonManager(
                                            lessonObj.getString("name"),
                                            lessonObj.getString("time"),
                                            lessonObj.getString("location"),
                                            lessonObj.getString("credit"),
                                            lessonObj.getString("id")
                                    );
                                    lessonManagers.add(lesson);
                                }

                                callback.onResult(lessonManagers);
                            } else {
                                String message = jsonResponse.optString("message", "Không lấy được danh sách bài học.");
                                Log.e(TAG, message);
                                callback.onResult(null);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Lỗi phân tích JSON: " + e.getMessage());
                            callback.onResult(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Lỗi khi gọi API: " + error.getMessage());
                        callback.onResult(null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", userId); // Truyền user_id để lấy danh sách bài học
                return params;
            }
        };

        queue.add(stringRequest);
    }
    public void getStudentManagers(final String lessonID, final StudentCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, STUDENTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                JSONArray studentsArray = jsonResponse.getJSONArray("students");
                                List<StudentManager> studentManager = new ArrayList<>();

                                for (int i = 0; i < studentsArray.length(); i++) {
                                    JSONObject studentObj = studentsArray.getJSONObject(i);
                                    StudentManager student = new StudentManager(
                                            studentObj.getString("id"),
                                            studentObj.getString("name")

                                    );
                                    studentManager.add(student);
                                }

                                callback.onResult(studentManager);
                            } else {
                                String message = jsonResponse.optString("message", "Không lấy được danh sách bài học.");
                                Log.e(TAG, message);
                                callback.onResult(null);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "Lỗi phân tích JSON: " + e.getMessage());
                            callback.onResult(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Lỗi khi gọi API: " + error.getMessage());
                        callback.onResult(null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lesson_id", lessonID); // Truyền user_id để lấy danh sách bài học
                Log.d(TAG, "Params: " + params.toString());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    // Định nghĩa interface Callback cho login
    public interface LoginCallback {
        void onResult(String isConnect);
    }
    // Định nghĩa interface Callback cho getLessonManagers
    public interface LessonCallback {
        void onResult(List<LessonManager> lessonManagers);
    }
    public interface StudentCallback{
        void onResult(List<StudentManager> studentManagers);
    }
}
