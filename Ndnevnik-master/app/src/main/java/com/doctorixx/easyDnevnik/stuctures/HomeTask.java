package com.doctorixx.easyDnevnik.stuctures;

import java.util.Objects;

public class HomeTask {
    private final HomeTaskType type;
    private final String data;

    public HomeTask(HomeTaskType type, String data) {
        this.type = type;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public HomeTaskType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeTask homeTask = (HomeTask) o;
        return type == homeTask.type && Objects.equals(data, homeTask.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }
}

