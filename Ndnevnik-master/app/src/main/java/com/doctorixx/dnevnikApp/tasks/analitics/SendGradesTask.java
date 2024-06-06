package com.doctorixx.dnevnikApp.tasks.analitics;

import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.Grade;

import java.util.List;
import java.util.Map;

public class SendGradesTask extends AsyncTask<Void, Void, Void> {

    private Map<String, List<Grade>> grades;

    public SendGradesTask(Map<String, List<Grade>> grades) {

        this.grades = grades;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            DnevnikAction.getInstance().getAnaliticsClient().sendGrades(new GradeContainer(grades));
        } catch (DnevnikNotOnlineException | DnevnikNoInternetConnection e) {
            return null;
        }
        return null;
    }
}
