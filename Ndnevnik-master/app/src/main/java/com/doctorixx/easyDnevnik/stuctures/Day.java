package com.doctorixx.easyDnevnik.stuctures;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private final List<Lesson> lessons;
    private final String description;
    private final String date;

    private final DayType type;

    private String name;

    public Day(List<Lesson> lessons, String date) {
        this.lessons = lessons;
        this.date = date;

        this.description = "";
        this.type = DayType.ORDINARY;
    }

    public Day(String description, String date, DayType type){
        this.date = date;
        this.description = description;


        this.lessons = new ArrayList<>();
        this.type = type;

    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DayType getType() {
        return type;
    }
}
