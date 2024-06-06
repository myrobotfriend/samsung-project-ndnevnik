package com.doctorixx.dnevnikApp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.doctorixx.dnevnikApp.other.AppState;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetAnnouncementsTask;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetFinalGradesTask;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetGradesTask;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetMessagesTask;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetProfileTask;
import com.doctorixx.dnevnikApp.tasks.dnevnik.WeekTask;
import com.doctorixx.dnevnikApp.tasks.dataclasses.AnnouncementListener;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnProfileTaskEnded;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnSuccess;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnTaskEndedListener;
import com.doctorixx.easyDnevnik.stuctures.Announcement;
import com.doctorixx.easyDnevnik.stuctures.FinalGades;
import com.doctorixx.easyDnevnik.stuctures.ProfileInfo;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;
import com.doctroixx.NDnevnik.R;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;

public class SyncDataActivity extends ResumableAppCompatActiviy implements OnTaskEndedListener, Runnable, OnSuccess<FinalGades> {

    private static final int TASK_COUNT = 8;

    private boolean wasRunned = false;
    private int endedTaks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);


        new WeekTask(this, 0).execute();
        new WeekTask(this, 1).execute();
        new WeekTask(this, -1).execute();
        new GetGradesTask(this).execute();
        saveGetProfileTask();
        new GetFinalGradesTask(this).execute();
        new GetMessagesTask(new OnSuccess<List<Message>>() {
            @Override
            public void run(List<Message> messages) {
                onEnded();
            }
        }).execute();

        new GetAnnouncementsTask(new AnnouncementListener() {
            @Override
            public void run(List<Announcement> announcements) {
                onEnded();
            }
        }).execute();
    }


    private void saveGetProfileTask() {
        OnProfileTaskEnded onGood = new OnProfileTaskEnded() {

            @Override
            public void onTaskEnded(ProfileInfo profileInfo) {
                if (profileInfo.getSchoolName() != null) {
                    FirebaseCrashlytics.getInstance().setCustomKey("school", profileInfo.getSchoolName());
                }
                new DataSerializer<>(ProfileInfo.class, getApplicationContext()).saveData(profileInfo, SerializationFilesNames.PROFILE_PATH);
                //Class method
                onEnded();
            }
        };

        new GetProfileTask(onGood, getApplicationContext()).execute();
    }

    private void onDataSynced() {
        if (!wasRunned) {
            wasRunned = true;
            AppState.put(AppState.FIRST_GOOD_LOGIN, getApplicationContext());
            startActivity(new Intent(getApplicationContext(), LauncherActivity.class));
        }

    }

    @Override
    public void onEnded() {
        run();
    }

    @Override
    public void run() {
        endedTaks++;
        if (endedTaks >= TASK_COUNT) onDataSynced();
    }

    @Override
    public void run(FinalGades finalGades) {
        onEnded();
    }
}