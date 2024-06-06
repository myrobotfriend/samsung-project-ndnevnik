package com.doctorixx.dnevnikApp.singeltons.containers;

import com.doctorixx.easyDnevnik.stuctures.Lesson;

public class LessonInfoData {

    private static LessonInfoData instance;

    private Lesson lesson;
    private String date;

    private LessonInfoData() {
    }

    public static LessonInfoData getInstance() {
        if (instance == null) instance = new LessonInfoData();
        return instance;
    }


    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
