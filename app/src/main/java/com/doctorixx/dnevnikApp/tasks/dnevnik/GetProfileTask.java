package com.doctorixx.dnevnikApp.tasks.dnevnik;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.activities.LauncherActivity;
import com.doctorixx.dnevnikApp.other.AppState;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnProfileTaskEnded;
import com.doctorixx.easyDnevnik.exceptions.DnevnikConnectException;
import com.doctorixx.easyDnevnik.stuctures.ProfileInfo;

public class GetProfileTask extends AsyncTask<Void, Void, ProfileInfo> {

    private OnProfileTaskEnded onGoodListener;
    private Context ctx;

    public GetProfileTask(OnProfileTaskEnded onGoodListener, Context ctx) {

        this.onGoodListener = onGoodListener;
        this.ctx = ctx;
    }

    @Override
    protected ProfileInfo doInBackground(Void... voids) {
        try {
            return DnevnikAction.getInstance().getDnevnik().getProfileInfo();
        } catch (DnevnikConnectException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ProfileInfo profileInfo) {
        super.onPostExecute(profileInfo);

        try {
            assert profileInfo != null;
            if (profileInfo.getEducationClass().getClassNumber() <= 0) {
                throw new Exception("PROFILE");
            } else {
                onGoodListener.onTaskEnded(profileInfo);
            }
        } catch (AssertionError ignored) {

        } catch (Exception e) {
            if (e.getMessage() == "PROFILE") {
                AppState.put(AppState.NOT_STUDENT_USER, ctx);
                ctx.startActivity(new Intent(ctx, LauncherActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        }
    }
}
