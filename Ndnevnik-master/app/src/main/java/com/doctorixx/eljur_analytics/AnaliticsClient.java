package com.doctorixx.eljur_analytics;

import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;

import java.util.Map;

public interface AnaliticsClient {

    String getHost();

    String authAndGetToken(String login, String password, String education_class,
                           String school, String token) throws DnevnikNotOnlineException, DnevnikNoInternetConnection;

    void sendGrades(GradeContainer gradeContainer) throws DnevnikNotOnlineException, DnevnikNoInternetConnection;

    float getClassAverage();

    float getClassAverage(String period);

    float getMyAverage();

    float getMyAverage(String period);

    float getMyAverageBySubject(String subject);

    float getMyAverageBySubject(String subject, String period);

    float getClassAverageBySubject(String subject);

    float getClassAverageBySubject(String subject, String period);

    Map<String, Integer> getGradesByDate(String subject, String date) throws DnevnikNotOnlineException, DnevnikNoInternetConnection;

    void setAuthToken(String token);

}
