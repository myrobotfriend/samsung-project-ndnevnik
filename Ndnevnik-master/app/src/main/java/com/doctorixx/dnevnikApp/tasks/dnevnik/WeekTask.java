package com.doctorixx.dnevnikApp.tasks.dnevnik;

import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.AccountManager;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.WeekContainer;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.singeltons.Logger;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnTaskEndedListener;
import com.doctorixx.easyDnevnik.Dnevnik;
import com.doctorixx.easyDnevnik.exceptions.DnevnikConnectException;
import com.doctorixx.easyDnevnik.stuctures.Week;


public class WeekTask extends AsyncTask<Void, Void, Void> {
    private boolean error = false;
    private Week out;
    private int weekIndex;

    private OnTaskEndedListener listener;
    private Runnable onErrorListener;

    public WeekTask(int weekIndex) {
        this.weekIndex = weekIndex;
    }

    public WeekTask(OnTaskEndedListener listener, int weekIndex) {
        this.listener = listener;
        this.weekIndex = weekIndex;

    }

    public WeekTask(OnTaskEndedListener listener, Runnable onError, int weekIndex) {
        this(listener, weekIndex);

        onErrorListener = onError;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        out = getWeek();
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (!error && (out != null)) {
            if (out.getDays().isEmpty()) {
                AccountManager.TrylogoutAndStartAuthActivity(DnevnikAction.getInstance().getContext());
            }
            DnevnikAction.getInstance().addWeek(weekIndex, out);
            if (listener != null) listener.onEnded();
        } else {
            if (onErrorListener != null) onErrorListener.run();
        }
    }

    private Week getWeek() {
        Dnevnik dnevnik = DnevnikAction.getInstance().getDnevnik();
        try {
            //TODO: Fix unsessary requests
            //Now this bugged (not display data
            //assert !DnevnikAction.getInstance().isContrainWeekIndex(weekIndex);

            return dnevnik.getWeekByIndex(weekIndex);
        } catch (DnevnikConnectException e) {
            WeekContainer weekContainer = new DataSerializer<>(WeekContainer.class, DnevnikAction.getInstance().getContext()).
                    openData(SerializationFilesNames.WEEK_CONTAINER_PATH);

            if (weekContainer != null) {
                if (weekContainer.getWeekMap().containsKey(weekIndex)) {
                    Logger.getInstance().debug("Restored data from storage");
                    return weekContainer.getWeekMap().get(weekIndex);
                }
            }
        } catch (AssertionError e) {
            Logger.getInstance().debug("Restored data from session storage");
            return DnevnikAction.getInstance().getWeekbyIndex(weekIndex);
        }

        error = true;
        return null;
    }

}
