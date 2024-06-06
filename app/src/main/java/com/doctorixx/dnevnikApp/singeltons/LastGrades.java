package com.doctorixx.dnevnikApp.singeltons;

import android.content.Context;

import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.LastGradesContainer;
import com.doctorixx.easyDnevnik.stuctures.Grade;

import java.util.ArrayList;
import java.util.List;

//TODO: ...
public class LastGrades {
    private static LastGrades instance;

    private final Context ctx;
    private LastGradesContainer container;

    private LastGrades(Context ctx) {
        this.ctx = ctx;
        initGrades();
    }

    public static LastGrades getInstance(Context ctx) {
        if (instance == null) instance = new LastGrades(ctx);
        return instance;
    }

    public List<Grade> getAllGrades(){
        return null;
    }

    public List<Grade> getUnReadGrades(){
        return null;
    }

    public void readGrade(){
        
    }


    private void initGrades(){
        LastGradesContainer localGradeContainer = new DataSerializer<LastGradesContainer>
                (LastGradesContainer.class,ctx)
                .openData(SerializationFilesNames.LAST_GRADE_CONTAINER_PATH);
        if (localGradeContainer == null) {
            localGradeContainer = new LastGradesContainer(new ArrayList<>(), new ArrayList<>());
        }
        container = localGradeContainer;
    }

}
