package com.example.myapplication.Class;
import com.example.myapplication.Class.User;

public class UserManager {
    private static UserManager instance;
    private User user;

    // Constructor private để không cho tạo instance bên ngoài
    private UserManager() { }

    // Phương thức lấy instance duy nhất
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Phương thức set User
    public void setUser(User user) {
        this.user = user;
    }

    // Phương thức get User
    public User getUser() {
        return user;
    }

    // Kiểm tra xem User đã đăng nhập hay chưa
    public boolean isLoggedIn() {
        return user != null;
    }

    // Phương thức để clear thông tin User (Logout)
    public void clearUser() {
        user = null;
    }
}