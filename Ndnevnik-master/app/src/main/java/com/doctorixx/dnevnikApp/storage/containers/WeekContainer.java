package com.doctorixx.dnevnikApp.storage.containers;

import com.doctorixx.easyDnevnik.stuctures.Week;

import java.util.HashMap;
import java.util.Map;

public class WeekContainer {

    private Map<Integer, Week> weekMap = new HashMap<>();

    public WeekContainer(Map<Integer, Week> weekMap) {
        this.weekMap = weekMap;
    }

    public Map<Integer, Week> getWeekMap() {
        return weekMap;
    }

}
