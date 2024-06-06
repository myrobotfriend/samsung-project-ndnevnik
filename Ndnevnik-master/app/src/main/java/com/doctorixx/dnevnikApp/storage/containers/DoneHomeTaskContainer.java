package com.doctorixx.dnevnikApp.storage.containers;

import com.doctorixx.easyDnevnik.stuctures.HomeTask;

import java.util.List;

public class DoneHomeTaskContainer {

    private final List<HomeTask> homeTasks;

    public DoneHomeTaskContainer(List<HomeTask> homeTasks) {
        this.homeTasks = homeTasks;
    }

    public List<HomeTask> getHomeTasks() {
        return homeTasks;
    }
}
