package com.doctorixx.dnevnikApp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Utils {

    public static int createID(){
        return createID(0);
    }

    public static int createID(int count){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("HHmmssSS",  Locale.US).format(now));
        return id - count;
    }
}
