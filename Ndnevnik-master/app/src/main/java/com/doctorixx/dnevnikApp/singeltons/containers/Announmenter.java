package com.doctorixx.dnevnikApp.singeltons.containers;

import com.doctorixx.easyDnevnik.stuctures.Announcement;

public class Announmenter {

    private static Announmenter instance;

    private Announcement announcement;

    private Announmenter(){}

    public static Announmenter getInstance() {
        if (instance == null) instance = new Announmenter();
        return instance;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
