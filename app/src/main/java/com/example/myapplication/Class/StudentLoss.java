package com.example.myapplication.Class;

public class StudentLoss {
    private  String id;
    private String count;

    public StudentLoss() {
    }

    public StudentLoss(String id, String count) {
        this.id = id;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StudentLoss{" +
                "id='" + id + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
