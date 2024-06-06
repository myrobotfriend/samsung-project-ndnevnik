package com.doctorixx.dnevnikApp.tasks.dnevnik;

import android.content.Context;
import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.other.AuthData;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.singeltons.Logger;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.WeekContainer;
import com.doctorixx.easyDnevnik.exceptions.DnevnikAuthException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;

public class AuthTask extends AsyncTask<Void, Void, Void> {
    private boolean error = false;

    private final Runnable action;
    private final Context context;
    private boolean isExecute = true;


    public AuthTask(Context ctx, Runnable action) {
        this.action = action;
        this.context = ctx;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            TryRestoreData();
            if (isExecute){
                AuthData authData = AuthData.get(context);
                DnevnikAction.getInstance().getDnevnik().auth(
                        authData.getLogin(),
                        authData.getPassword()
                );
            }


        } catch (DnevnikNoInternetConnection | DnevnikNotOnlineException | DnevnikAuthException | NullPointerException e) {
            e.printStackTrace();
            Logger.getInstance().error(e.toString());
            TryRestoreData();
        }

        error = true;
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if (!error) {
            action.run();
        }
    }

    private void TryRestoreData() {
        WeekContainer weekContainer = new DataSerializer<>(WeekContainer.class, context).
                openData(SerializationFilesNames.WEEK_CONTAINER_PATH);
        if (weekContainer != null && weekContainer.getWeekMap().values().size() != 0) {
            action.run();
        }

    }

    public AuthTask setExecuteInternet(boolean value){
        isExecute = value;
        return this;
    }

}
