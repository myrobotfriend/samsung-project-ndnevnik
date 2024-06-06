package com.doctorixx.changelog;

import com.doctroixx.NDnevnik.BuildConfig;

public final class ChangeLog {
    private static final String versionName = BuildConfig.VERSION_NAME;

    private static final String date = "30.05.2024";

    private static final String[] changes = {
            "Исправлена контрастность дз на каникулы в темной теме",
    };

    private static final String title = String.format("Релиз от %s", date);
    private static final String prefixMsg = "";

    private static String msg;

    private static String buildMsg(){
        String pattern = "· %s \n";

        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(prefixMsg);
        stringBuilder.append("\n");
        stringBuilder.append("Что нового? \n");

        for (String change : changes) stringBuilder.append(String.format(pattern, change));

        return stringBuilder.toString();
    }

    public static String getMsg() {
        if (msg == null) msg = buildMsg();
        return msg;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static String getTitle() {
        return title;
    }

    public static String getDate() {
        return date;
    }

    public static String getPrefixMsg() {
        return prefixMsg;
    }

    public static String[] getChanges() {
        return changes;
    }
}

