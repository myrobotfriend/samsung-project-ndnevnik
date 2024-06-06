package com.doctorixx.dnevnikApp.storage.containers;

import com.doctorixx.easyDnevnik.stuctures.Announcement;

import java.util.List;

public class AnnouncementsContainer {

    private List<Announcement> announcements;

    public AnnouncementsContainer(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }
}
