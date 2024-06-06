package com.doctorixx.easyDnevnik;


import com.doctorixx.easyDnevnik.exceptions.DnevnikAuthException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikConnectException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.FinalGades;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.ProfileInfo;
import com.doctorixx.easyDnevnik.stuctures.Week;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface Dnevnik {

    void auth(String login, String password) throws DnevnikNoInternetConnection, DnevnikNotOnlineException,
            DnevnikAuthException;

    Map<String, List<Grade>> getGrades() throws DnevnikConnectException;

    FinalGades getFinalGrades() throws DnevnikConnectException;

    ProfileInfo getProfileInfo() throws DnevnikConnectException;

    Week getWeek() throws DnevnikConnectException;

    Week getWeekByIndex(int index) throws DnevnikConnectException;

    Week getWeekByDate(Calendar date);



}
