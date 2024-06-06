package com.doctorixx.changelog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.doctorixx.dnevnikApp.storage.SharedFileName;
import com.doctroixx.NDnevnik.BuildConfig;

public class ChangeLogStorage {
    public static int load(Context ctx) {
        SharedPreferences mSettings = ctx.getSharedPreferences(
                SharedFileName.appDataFileName, Context.MODE_PRIVATE);
        return mSettings.getInt("last_seen_version", -1);

    }

    @SuppressLint("CommitPrefEdits")
    public static void put(Context ctx) {
        SharedPreferences.Editor mSettings = ctx.getSharedPreferences(
                SharedFileName.appDataFileName,
                Context.MODE_PRIVATE).edit();
        mSettings.putInt("last_seen_version", BuildConfig.VERSION_CODE);
        mSettings.apply();
    }
}
