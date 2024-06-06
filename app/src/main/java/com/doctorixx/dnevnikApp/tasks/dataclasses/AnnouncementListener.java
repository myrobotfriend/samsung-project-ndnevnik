package com.doctorixx.dnevnikApp.tasks.dataclasses;

import com.doctorixx.easyDnevnik.stuctures.Announcement;

import java.util.List;

public interface AnnouncementListener {
    void run(List<Announcement> announcements);
}
