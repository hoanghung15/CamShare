package com.example.myapplication.Class;


import java.io.Serializable;

public class lessonSelection extends Lesson implements Serializable  {
    public lessonSelection() {
        super();
    }

    public lessonSelection(String name, String time, String location, String id, String credit) {
        super(name, time, location, id, credit);
    }


    @Override
    public String toString() {
        return "lessonSelection{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", time='" + getTime() + '\'' +
                ", credit=" + getCredit() +
                ", location='" + getLocation() + '\'' +
                '}';
    }
}
