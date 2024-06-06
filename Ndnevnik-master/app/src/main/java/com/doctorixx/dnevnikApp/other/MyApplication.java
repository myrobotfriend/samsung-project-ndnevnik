package com.doctorixx.dnevnikApp.other;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class MyApplication extends Application
        implements Application.ActivityLifecycleCallbacks {

    private int activityCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    public boolean isAppForeground() {
        return activityCount > 0;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        activityCount++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activityCount--;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }
}