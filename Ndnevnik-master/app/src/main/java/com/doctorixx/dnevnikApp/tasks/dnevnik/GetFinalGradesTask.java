package com.doctorixx.dnevnikApp.tasks.dnevnik;

import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnSuccess;
import com.doctorixx.easyDnevnik.exceptions.DnevnikConnectException;
import com.doctorixx.easyDnevnik.stuctures.FinalGades;

public class GetFinalGradesTask extends AsyncTask<Void, Void, FinalGades> {

    private final OnSuccess<FinalGades> onSuccess;

    public GetFinalGradesTask(OnSuccess<FinalGades> onSuccess) {
        this.onSuccess = onSuccess;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        displaySavedData();
    }

    @Override
    protected FinalGades doInBackground(Void... voids) {
        try {
            return DnevnikAction.getInstance().getDnevnik().getFinalGrades();
        } catch (DnevnikConnectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(FinalGades finalGades) {
        super.onPostExecute(finalGades);
        if (finalGades != null && finalGades.getOutHeaders().size() != 0) {
            runCallback(finalGades);
            saveData(finalGades);
        }

    }

    private void displaySavedData(){
        FinalGades grades = openData();
        if (grades != null && grades.getOutHeaders().size() != 0) runCallback(grades);
    }

    private void saveData(FinalGades finalGades) {
        new DataSerializer<>(FinalGades.class,DnevnikAction.getInstance().getContext())
                .saveData(finalGades,SerializationFilesNames.FINAL_GRADE_PATH);
    }

    private FinalGades openData() {
        return new DataSerializer<>(FinalGades.class,DnevnikAction.getInstance().getContext())
                .openData(SerializationFilesNames.FINAL_GRADE_PATH);
    }

    private void runCallback(FinalGades finalGades){
        if (onSuccess != null) onSuccess.run(finalGades);
    }
}
