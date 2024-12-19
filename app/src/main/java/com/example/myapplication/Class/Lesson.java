package com.example.myapplication.Class;


import java.io.Serializable;

public class Lesson implements Serializable {
    private String name;
    private String time;
    private String location;
    private String id;
    private String credit;

    public Lesson() {
    }

    public Lesson(String name, String time, String location, String id, String credit) {
        this.name = name;
        this.time = time;
        this.location = location;
        this.id = id;
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                ", id='" + id + '\'' +
                ", credit=" + credit +
                '}';
    }
}
