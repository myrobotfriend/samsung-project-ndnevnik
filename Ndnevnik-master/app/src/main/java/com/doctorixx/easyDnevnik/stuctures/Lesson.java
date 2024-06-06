package com.doctorixx.easyDnevnik.stuctures;

import java.util.List;

public class Lesson {
    private final int lessonNumber;

    private final String subject;
    private final String time;
    private final List<Grade> grades;

    private final List<HomeTask> homeTasks;



    public Lesson(int lessonNumber, String subject, String time, List<Grade> grades, List<HomeTask> homeTasks) {
        this.lessonNumber = lessonNumber;
        this.subject = subject;
        this.time = time;
        this.grades = grades;
        this.homeTasks = homeTasks;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public String getStringLessonNumber(){
        if (lessonNumber < 0) return " ";
        return String.valueOf(lessonNumber);
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public List<HomeTask> getHomeTasks() {
        return homeTasks;
    }
}
