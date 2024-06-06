package com.doctorixx.dnevnikApp.works;

import android.content.Context;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public final class WorkStarter {

    public static void activateWorks(Context ctx) {
        stopAllWorks(ctx);
        PeriodicWorkRequest myWorkRequest = new PeriodicWorkRequest.Builder(BackGroundWork.class,
                15, TimeUnit.MINUTES).build();
        WorkManager.getInstance(ctx).enqueue(myWorkRequest);

    }

    public static void stopAllWorks(Context ctx) {
        WorkManager workManager = WorkManager.getInstance(ctx);
        workManager.pruneWork();
        workManager.cancelAllWork();
    }
}
