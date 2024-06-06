package com.doctorixx.dnevnikApp.tasks.analitics;

import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnSuccess;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;

import java.util.Map;

public class GetGradesByDateTask extends AsyncTask<Void, Void, Map<String, Integer>> {

    private OnSuccess<Map<String, Integer>> callback;
    private String subject;
    private String date;

    public GetGradesByDateTask(OnSuccess<Map<String, Integer>> callback, String subject, String date) {
        this.callback = callback;
        this.subject = subject;
        this.date = date;
    }

    @Override
    protected Map<String, Integer> doInBackground(Void... voids) {
        try {
            return DnevnikAction.getInstance().getAnaliticsClient().getGradesByDate(
                    subject, date
            );
        } catch (DnevnikNotOnlineException | DnevnikNoInternetConnection e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Map<String, Integer> out) {
        super.onPostExecute(out);
        if (out != null && !out.isEmpty()) callback.run(out);
    }
}
