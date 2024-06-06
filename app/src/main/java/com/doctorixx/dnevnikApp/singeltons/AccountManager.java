package com.doctorixx.dnevnikApp.singeltons;

import android.content.Context;
import android.content.Intent;

import com.doctorixx.dnevnikApp.activities.LauncherActivity;
import com.doctorixx.dnevnikApp.other.AppState;
import com.doctorixx.network.SaverCookieJar;

public class AccountManager {

    public static void logout(Context ctx) {
        AppState.put(AppState.EXIT, ctx);
        SaverCookieJar.clearData(ctx);
    }

    public static void TrylogoutAndStartAuthActivity(Context ctx) {
        if (stateIsLogined(ctx)) {
            logout(ctx);
            ctx.startActivity(new Intent(ctx, LauncherActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

    }

    private static boolean stateIsLogined(Context ctx) {
        AppState state = AppState.load(ctx);
        return state.equals(AppState.MAIN) ||
                state.equals(AppState.TEST_NDEVNIK) ||
                state.equals(AppState.NOT_STUDENT_USER);
    }

}
