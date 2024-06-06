package com.doctorixx.dnevnikApp.tasks.analitics;

import android.content.Context;
import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.eljur_analytics.AnaliticsClient;

public class TestAnaliticsTasks extends AsyncTask<Void, Void, Void> {
    final Context ctx;

    public TestAnaliticsTasks(Context context) {
        ctx = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AnaliticsClient analitics = DnevnikAction.getInstance(ctx).getAnaliticsClient();

        try {
            analitics.authAndGetToken(
                    "android",
                    "testpassword",
                    "8U",
                    "school_800",
                    "ignored_paramentr"
            );

//            Map<String, Integer> grades = analitics.getGradesByDate("Алгебра", "2023-01-30");
            System.out.println();
        } catch (Exception e) {
            System.out.println();
        }

        return null;
    }
}
