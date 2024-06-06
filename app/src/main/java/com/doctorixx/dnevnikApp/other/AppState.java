package com.doctorixx.dnevnikApp.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.doctorixx.dnevnikApp.storage.SharedFileName;

public enum AppState {
    FIRST_APP_START(-9999),
    NOT_STUDENT_USER(-10000),

    TEST_NDEVNIK(-5000),

    NEED_SYNC(-3),

    EXIT(-2),

    FIRST_GOOD_LOGIN(0),

    NEED_LOGIN(-1),

    MAIN(1);

    private final int value;

    private AppState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AppState getStateByValue(int value) {
        AppState[] states = AppState.values();
        for (AppState state : states) {
            if (state.getValue() == value) return state;
        }

        return null;
    }

    public static AppState load(Context ctx) {
        SharedPreferences mSettings = ctx.getSharedPreferences(
                SharedFileName.appDataFileName, Context.MODE_PRIVATE);
        return AppState.getStateByValue(
                mSettings.getInt("state", AppState.FIRST_APP_START.getValue())
        );
    }

    @SuppressLint("CommitPrefEdits")
    public static void put(AppState state, Context ctx) {
        SharedPreferences.Editor mSettings = ctx.getSharedPreferences(
                SharedFileName.appDataFileName,
                Context.MODE_PRIVATE).edit();
        mSettings.putInt("state", state.getValue());
        mSettings.apply();
    }

}
