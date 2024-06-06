package com.doctorixx.dnevnikApp.singeltons;

import android.content.Context;

import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.dnevnikApp.storage.containers.WeekContainer;
import com.doctorixx.easyDnevnik.Dnevnik;
import com.doctorixx.easyDnevnik.instances.NighegorodskayaOblastDnevnik;
import com.doctorixx.easyDnevnik.stuctures.Week;
import com.doctorixx.eljur_analytics.AnaliticsClient;
import com.doctorixx.eljur_analytics.instances.ProdAnaliticsClient;
import com.doctorixx.network.RequestClient;

import java.util.HashMap;

public class DnevnikAction {

    private static DnevnikAction instance;

    private final Context ctx;
    private WeekContainer weekContainer;
    private DataSerializer<WeekContainer> saver;
    private GradeContainer grades;


    private  Dnevnik dnevnik;
    private final AnaliticsClient analiticsClient;

    private DnevnikAction(Context ctx) {
        this.ctx = ctx;
        System.out.println("Creating");
        dnevnik = new NighegorodskayaOblastDnevnik(ctx);
        analiticsClient = new ProdAnaliticsClient(new RequestClient(ctx));
        saver = new DataSerializer<>(WeekContainer.class, ctx);
        initWeekContainer();
    }

    private void initWeekContainer() {
        WeekContainer tempWeek = saver.openData(SerializationFilesNames.WEEK_CONTAINER_PATH);
        if (tempWeek != null) weekContainer = tempWeek;
        else weekContainer = new WeekContainer(new HashMap<>());
    }

    public static DnevnikAction getInstance(Context ctx) {
        if (instance == null) instance = new DnevnikAction(ctx);
        return instance;
    }

    public static DnevnikAction getInstance() {
        return instance;
    }

    public static void recreateInstance(Context ctx) {
        if (instance == null) instance = new DnevnikAction(ctx);
    }

    //Getters and Setters (fields not instance)
    public Dnevnik getDnevnik() {
        return dnevnik;
    }

    public void addWeek(int index, Week week) {
        weekContainer.getWeekMap().put(index, week);

        saver.saveData(weekContainer, SerializationFilesNames.WEEK_CONTAINER_PATH);
    }

    public boolean isContrainWeekIndex(int index) {
        return weekContainer.getWeekMap().containsKey(index);
    }

    public Week getWeekbyIndex(int index) {
        return weekContainer.getWeekMap().get(index);
    }


    public Context getContext() {
        return ctx;
    }

    public GradeContainer getGrades() {
        return grades;
    }

    public void setGrades(GradeContainer grades) {
        this.grades = grades;
    }

    public void setDnevnik(Dnevnik dnevnik) {
        this.dnevnik = dnevnik;
    }

    public AnaliticsClient getAnaliticsClient() {
        return analiticsClient;
    }
}
