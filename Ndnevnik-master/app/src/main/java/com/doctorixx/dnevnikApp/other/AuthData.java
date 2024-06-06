package com.doctorixx.dnevnikApp.other;

import android.content.Context;
import android.content.SharedPreferences;

import com.doctorixx.dnevnikApp.storage.SharedFileName;

public class AuthData {

    private static final String loginFieldName = "Auth.login";
    private static final String passwordFieldName = "Aauth.pass";

    private final String login;
    private final String password;
    private final Context context;

    public AuthData(String login, String password, Context context) {
        this.login = login;
        this.password = password;
        this.context = context;
    }

    public void put() {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                SharedFileName.authFileName, Context.MODE_PRIVATE).edit();

        editor.putString(loginFieldName, login);
        editor.putString(passwordFieldName, password);

        editor.apply();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static AuthData get(Context ctx) {
        SharedPreferences mSettings = ctx.getSharedPreferences(SharedFileName.authFileName, Context.MODE_PRIVATE);

        String login = mSettings.getString(loginFieldName, "__NULL__");
        String password = mSettings.getString(passwordFieldName, "__NULL__");

        if (login == "__NULL__" || password == "__NULL__") return null;
        return new AuthData(login, password, ctx);

    }

    public static void clear(Context ctx){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(
                SharedFileName.authFileName, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

    }

}
