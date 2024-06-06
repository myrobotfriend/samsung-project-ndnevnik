package com.doctorixx.dnevnikApp.works;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.doctorixx.dnevnikApp.activities.LauncherActivity;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.dnevnikApp.storage.containers.WeekContainer;
import com.doctorixx.dnevnikApp.utils.Utils;
import com.doctorixx.easyDnevnik.Dnevnik;
import com.doctorixx.easyDnevnik.exceptions.DnevnikException;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.Week;
import com.doctroixx.NDnevnik.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackGroundWork extends Worker {

    private static Dnevnik dnevnik;
    private static Context ctx;

    public BackGroundWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        ctx = getApplicationContext();

        DnevnikAction.recreateInstance(ctx);


        dnevnik = DnevnikAction.getInstance(ctx).getDnevnik();
    }


    @NonNull
    @Override
    public Result doWork() {
        Log.d("[WORKER]", "started");

//            sendNotification();
        requestGrades();

//        startMyService();

        requestWeek(1);
        requestWeek(0);
        requestWeek(-1);


        Log.d("[WORKER]", "good end");
        return Result.success();
    }




    private void requestWeek(int index) {
        try {
            Log.d("[WORKER]", "started | " + index);
            Week week = dnevnik.getWeekByIndex(index);
            assert week != null;
            saveWeek(week, index);
            Log.d("[WORKER]", String.format("all good (%d)", index));
        } catch (DnevnikException | AssertionError e) {
            e.printStackTrace();
            Log.d("[WORKER]", "errored");
            Log.e("[WORKER]", e.toString());
        }
    }


    private void requestGrades() {
        try {
            Log.d("[WORKER]", "started grades");
            Map<String, List<Grade>> grades = dnevnik.getGrades();
            assert grades != null;
            if (grades.isEmpty()) {
                throw new AssertionError();
            }
            GradeContainer gradeContainer = new GradeContainer(grades);
            if (!gradesEquals(gradeContainer)) sendNotification(
                    "Новые оценки!", "Пора проверить дневник"
            );
            saveGrades(gradeContainer);

            Log.d("[WORKER]", "all good grades)");
//            sendNotification("Выполнение завершено без ошибки!", ")))");

        } catch (DnevnikException | AssertionError e) {
//            sendNotification("Выполнение завершено с ошибкой!", ")))");
            e.printStackTrace();
            Log.d("[WORKER]", "errored");
            Log.e("[WORKER]", e.toString());
        }
    }

    private void saveWeek(Week week, int index) {
        DataSerializer<WeekContainer> serializer = new DataSerializer<>(WeekContainer.class, ctx);
        WeekContainer savedWeekContainer = serializer.openData(SerializationFilesNames.WEEK_CONTAINER_PATH);
        if (savedWeekContainer == null) savedWeekContainer = new WeekContainer(new HashMap<>());

        savedWeekContainer.getWeekMap().put(index, week);
        serializer.saveData(savedWeekContainer, SerializationFilesNames.WEEK_CONTAINER_PATH);
    }

    private void saveGrades(GradeContainer container) {
        DataSerializer<GradeContainer> serializer = new DataSerializer<>(GradeContainer.class, ctx);

        serializer.saveData(container, SerializationFilesNames.GRADE_CONTAINER_PATH);
    }


    private boolean gradesEquals(GradeContainer container) {
        DataSerializer<GradeContainer> serializer = new DataSerializer<>(GradeContainer.class, ctx);
        GradeContainer oldGrades = serializer.openData(SerializationFilesNames.GRADE_CONTAINER_PATH);
        if (oldGrades == null) return true;
        boolean result = container.equals(oldGrades);
        Log.i("[WORKER]", "gradeEquals = " + result);
        return result;
    }

    private void sendNotification(String title, String body) {
        NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(ctx, LauncherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 1, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx, "ch_1")
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        nm.notify(Utils.createID(5), mBuilder.build());

    }

}
