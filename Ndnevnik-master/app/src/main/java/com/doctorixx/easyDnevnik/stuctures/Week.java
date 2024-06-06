package com.doctorixx.easyDnevnik.stuctures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week {

    private Map<String,HomeTask> tasks;

    private final List<Day> days;

    {
        tasks = new HashMap<>();
    }

    public Week(Day monday, Day tuesday, Day wednesday,
                Day thursday, Day friday, Day saturday, Day sunday) {
        days = new ArrayList<>();

        days.add(monday);
        days.add(tuesday);
        days.add(wednesday);
        days.add(thursday);
        days.add(friday);
        days.add(saturday);
        days.add(sunday);
    }

    public Week(List<Day> days){
        this.days = days;
    }


    public List<Day> getDays() {
        return days;
    }

    public Day getMonday() {
        return days.get(0);
    }

    public Day getTuesday() {
        return days.get(1);
    }

    public Day getWednesday() {
        return days.get(2);
    }

    public Day getThursday() {
        return days.get(3);
    }

    public Day getFriday() {
        return days.get(4);
    }

    public Day getSaturday() {
        return days.get(5);
    }

    public Day getSunday() {
        return days.get(6);
    }

    public Map<String, HomeTask> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, HomeTask> tasks) {
        this.tasks = tasks;
    }
}


