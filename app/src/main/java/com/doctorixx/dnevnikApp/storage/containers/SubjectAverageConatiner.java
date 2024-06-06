package com.doctorixx.dnevnikApp.storage.containers;

import java.util.Map;

public class SubjectAverageConatiner {

    private static SubjectAverageConatiner instance;

    private final Map<String, Float> averages;

    public SubjectAverageConatiner(Map<String, Float> averages) {
        this.averages = averages;
    }

    public static SubjectAverageConatiner getInstance() {
        return instance;
    }

    public static void setInstance(SubjectAverageConatiner instance) {
        SubjectAverageConatiner.instance = instance;
    }

    public Map<String, Float> getAverages() {
        return averages;
    }
}
