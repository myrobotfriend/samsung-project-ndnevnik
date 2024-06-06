package com.doctorixx.dnevnikApp.other;

public class Date {

    private final int year, mouth, day;

    public Date(String str, String separator) {
        String[] date = str.split(separator);
        year = Integer.parseInt(date[0]);
        mouth = Integer.parseInt(date[1]);
        day = Integer.parseInt(date[2]);
    }

    public boolean after(Date date) {
        if (year > date.year) return true;
        else if (date.year > year) return false;

        if (mouth > date.mouth) return true;
        else if (date.mouth > mouth) return false;

        if (day > date.day) return true;
        else if (date.day > day) return false;

        return false;
    }

    public boolean before(Date date) {
        return !after(date);
    }

    public int getYear() {
        return year;
    }

    public int getMouth() {
        return mouth;
    }

    public int getDay() {
        return day;
    }
}
