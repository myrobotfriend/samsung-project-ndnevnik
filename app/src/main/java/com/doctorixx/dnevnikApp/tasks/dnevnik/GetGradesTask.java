package com.doctorixx.dnevnikApp.tasks.dnevnik;

import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.dnevnikApp.tasks.analitics.SendGradesTask;
import com.doctorixx.easyDnevnik.exceptions.DnevnikException;
import com.doctorixx.easyDnevnik.stuctures.Grade;

import java.util.List;
import java.util.Map;

public class GetGradesTask extends AsyncTask<Void, Void, Void> {

    private boolean error = false;
    private Runnable callback;
    private Map<String, List<Grade>> out;
    private final DataSerializer<GradeContainer> dataSerializer = new DataSerializer<GradeContainer>(GradeContainer.class, DnevnikAction.getInstance().getContext());

    public GetGradesTask(Runnable runnable) {
        callback = runnable;
    }

    public GetGradesTask() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        restoreDataAndDisplay();
    }

    @Override
    protected Void doInBackground(Void... voids) {


        try {
            out = DnevnikAction.getInstance().getDnevnik().getGrades();
            dataSerializer.saveData(new GradeContainer(out), SerializationFilesNames.GRADE_CONTAINER_PATH);
        } catch (DnevnikException e) {
            e.printStackTrace();

            error = true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        if (out != null && !error && out.size() != 0) {
            saveData();
            new SendGradesTask(out).execute();
        }
    }

    private void saveData() {
        DnevnikAction.getInstance().setGrades(new GradeContainer(out));
        callback.run();
    }

    private void restoreDataAndDisplay() {
        GradeContainer grades = dataSerializer.openData(SerializationFilesNames.GRADE_CONTAINER_PATH);
        if (grades != null && grades.getGrades().size() != 0) {
            out = grades.getGrades();
            saveData();
        }
    }
}
