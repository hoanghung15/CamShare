package com.example.myapplication.Class;

public class LessonManager extends  Lesson {
    public LessonManager() {
    }

    public LessonManager(String name, String time, String location, String id, String credit) {
        super(name, time, location, id, credit);
    }



    public String getFormattedDetails() {
        return String.format("Mã: %s, Tên: %s, Tín chỉ: %d", getId(), getName(), getCredit());
    }
}
