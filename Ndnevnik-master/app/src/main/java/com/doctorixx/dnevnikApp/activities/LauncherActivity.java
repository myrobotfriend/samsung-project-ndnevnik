package com.doctorixx.dnevnikApp.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.doctorixx.dnevnikApp.other.AppState;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.singeltons.Logger;
import com.doctorixx.dnevnikApp.tasks.analitics.TestAnaliticsTasks;
import com.doctorixx.dnevnikApp.works.WorkStarter;
import com.doctorixx.easyDnevnik.instances.TestDnevnik;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LauncherActivity extends ResumableAppCompatActiviy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFireBaseCloudMessaging();
        route();
    }

    @Override
    protected void onResume() {
        super.onResume();

        route();
    }

    private void route() {
        final AppState appState = AppState.load(getApplicationContext());

        DnevnikAction.getInstance(getApplicationContext());
        Logger.getInstance().debug("Current state is: " + appState.getValue());
        switch (appState) {
            case FIRST_APP_START:
                initMessageChannel();
                setState(AppState.NEED_LOGIN);
                recreate();
                break;
            case NEED_SYNC:
                startActivity(new Intent(getApplicationContext(), SyncDataActivity.class));
                break;
            case EXIT:
                WorkStarter.stopAllWorks(getApplicationContext());
                setState(AppState.NEED_LOGIN);
                recreate();
                break;
            case FIRST_GOOD_LOGIN:
                setState(AppState.MAIN);
                recreate();
                break;
            case NOT_STUDENT_USER:
            case MAIN:
                new TestAnaliticsTasks(getApplicationContext()).execute();
                WorkStarter.activateWorks(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case NEED_LOGIN:
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
                break;
            case TEST_NDEVNIK:
                DnevnikAction.getInstance().setDnevnik(new TestDnevnik());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

    }

    private void setState(AppState state) {
        AppState.put(state, getApplicationContext());
    }

    private void initMessageChannel() {
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel get_channel = nm.getNotificationChannel("aboba");

            NotificationChannel channel = new NotificationChannel("ch_1", "my Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setDescription("Hello");

            nm.createNotificationChannel(channel);
        }
    }

    private void initFireBaseCloudMessaging(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Logger.getInstance().error("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = "MSG token -> " + token;
                        Logger.getInstance().info(msg);

                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("default");
    }

}