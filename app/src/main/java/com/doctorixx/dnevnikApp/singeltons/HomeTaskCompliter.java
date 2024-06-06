package com.doctorixx.dnevnikApp.singeltons;

import android.content.Context;

import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.DoneHomeTaskContainer;
import com.doctorixx.easyDnevnik.stuctures.HomeTask;

import java.util.ArrayList;
import java.util.List;

public class HomeTaskCompliter {

    private static HomeTaskCompliter instance;

    private DoneHomeTaskContainer homeTaskContainer;

    private final Context ctx;

    private HomeTaskCompliter(Context ctx) {
        this.ctx = ctx;
        readContainer();
    }

    public static HomeTaskCompliter getInstance(Context ctx) {
        if (instance == null) instance = new HomeTaskCompliter(ctx);
        return instance;
    }


    public boolean isDone(HomeTask homeTask) {
        return isContainerContains(homeTask);
    }

    public void doneHomeWork(HomeTask homeTask) {
        homeTaskContainer.getHomeTasks().add(homeTask);
        onContainerDataChanged();
    }

    public void undoHomeWork(HomeTask homeTask) {
        List<HomeTask> homeTasks = homeTaskContainer.getHomeTasks();
        if (isContainerContains(homeTask)) {
            homeTasks.remove(homeTask);
            onContainerDataChanged();
        }
    }

    private boolean isContainerContains(HomeTask homeTask) {
        return homeTaskContainer.getHomeTasks().contains(homeTask);
    }

    private void onContainerDataChanged() {
        writeContainer();
    }

    private void readContainer() {
        DoneHomeTaskContainer container = new DataSerializer<>(DoneHomeTaskContainer.class, ctx)
                .openData(SerializationFilesNames.DONE_HOMETASK_CONTAINER_PATH);

        if (container == null) container = new DoneHomeTaskContainer(new ArrayList<>());
        homeTaskContainer = container;

    }

    private void writeContainer() {
        new DataSerializer<>(DoneHomeTaskContainer.class, ctx)
                .saveData(homeTaskContainer, SerializationFilesNames.DONE_HOMETASK_CONTAINER_PATH);

    }

}
