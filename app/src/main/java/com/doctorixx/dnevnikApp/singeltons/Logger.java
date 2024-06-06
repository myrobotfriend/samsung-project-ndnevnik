package com.doctorixx.dnevnikApp.singeltons;

import android.util.Log;

public class Logger {

    private static final Logger instance = new Logger();

    private static final String LOG_TAG = "NDnevnik";

    private Logger() {

    }

    public static Logger getInstance() {
        return instance;
    }

    public void error(String msg) {
        Log.e(LOG_TAG,msg);
    }

    public void info(String msg) {
        Log.i(LOG_TAG,msg);
    }

    public void debug(String msg) {
        Log.d(LOG_TAG,msg);
    }

}
