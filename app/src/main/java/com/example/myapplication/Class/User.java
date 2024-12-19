package com.example.myapplication.Class;

public class User {
    private String id;
    private String email;
    private String password;
    private String name;
    private String avtUrl;
    private String role;

    // Constructor mặc định
    public User() {
    }

    public User(String id, String email, String password, String name, String avtUrl, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.avtUrl = avtUrl;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvtUrl() {
        return avtUrl;
    }

    public void setAvtUrl(String avtUrl) {
        this.avtUrl = avtUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", avtUrl='" + avtUrl + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
