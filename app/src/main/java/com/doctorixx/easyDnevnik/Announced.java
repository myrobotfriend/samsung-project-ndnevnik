package com.doctorixx.easyDnevnik;

import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.Announcement;

import java.util.List;

public interface Announced {

    List<Announcement> getAllAnnouncements() throws DnevnikNotOnlineException, DnevnikNoInternetConnection;

}
