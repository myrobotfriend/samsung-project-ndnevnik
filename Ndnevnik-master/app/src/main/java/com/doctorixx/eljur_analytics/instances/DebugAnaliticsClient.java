package com.doctorixx.eljur_analytics.instances;

import android.content.Context;

import com.doctorixx.dnevnikApp.other.AuthData;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.ProfileInfo;
import com.doctorixx.eljur_analytics.AnaliticsClient;
import com.doctorixx.eljur_analytics.structures.AuthResponse;
import com.doctorixx.network.RequestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class DebugAnaliticsClient implements AnaliticsClient {
    String SERVER_URL = "http://192.168.0.105:5000";

    private final RequestClient requestClient;

    private static String authToken;

    public DebugAnaliticsClient(RequestClient requestClient) {
        this.requestClient = requestClient;
    }

    @Override
    public String getHost() {
        return SERVER_URL;
    }

    @Override
    public String authAndGetToken(String login, String password, String education_class, String school, String token) throws DnevnikNotOnlineException, DnevnikNoInternetConnection {
        Map<String, String> jsonMap = new HashMap<>();

        Context ctx = DnevnikAction.getInstance().getContext();

        Gson gson = new GsonBuilder().create();
        ProfileInfo profile = new DataSerializer<>(ProfileInfo.class, ctx)
                .openData(SerializationFilesNames.PROFILE_PATH);

        AuthData authData = AuthData.get(ctx);

        jsonMap.put("login", authData.getLogin());
        jsonMap.put("password", authData.getPassword());
        jsonMap.put("class", profile.getEducationClass().toString());
        jsonMap.put("school", profile.getSchoolName());
        jsonMap.put("token", "IGNORED");

        String json = gson.toJson(jsonMap);

        String stringResp = requestClient.post(getHost() + "/auth", json);
        AuthResponse response = gson.fromJson(stringResp, AuthResponse.class);

        String localAuthToken = response.getData().get("token");

        authToken = localAuthToken;

        return localAuthToken;
    }

    @Override
    public void sendGrades(GradeContainer gradeContainer) throws DnevnikNotOnlineException, DnevnikNoInternetConnection {
        // TODO: 09.03.2023  
//        Gson gson = new GsonBuilder().create();
//
//        SendGradesRequest request = new SendGradesRequest(authToken, gradeContainer.getGrades());
//        String json = gson.toJson(request);
//
//        requestClient.post(getHost() + "/send_grades", json);
    }

    @Override
    public float getClassAverage() {
        return 0;
    }

    @Override
    public float getClassAverage(String period) {
        return 0;
    }

    @Override
    public float getMyAverage() {
        return 0;
    }

    @Override
    public float getMyAverage(String period) {
        return 0;
    }

    @Override
    public float getMyAverageBySubject(String subject) {
        return 0;
    }

    @Override
    public float getMyAverageBySubject(String subject, String period) {
        return 0;
    }

    @Override
    public float getClassAverageBySubject(String subject) {
        return 0;
    }

    @Override
    public float getClassAverageBySubject(String subject, String period) {
        return 0;
    }

    @Override
    public Map<String, Integer> getGradesByDate(String subject, String date) throws DnevnikNotOnlineException, DnevnikNoInternetConnection {
//        Map<String, String> jsonMap = new HashMap<>();
//
//        Gson gson = new GsonBuilder().create();
//
//        jsonMap.put("auth_token", this.authToken);
//        jsonMap.put("date", date);
//        jsonMap.put("subject", subject);
//
//        String json = gson.toJson(jsonMap);
//
//        String strResp = requestClient.post(getHost() + "/get_grades_by_date", json);
//        GradesByDateResponse response = gson.fromJson(strResp, GradesByDateResponse.class);
//
//        return response.getResult();
        // TODO: 09.03.2023  
        return new HashMap<>();
    }


    @Override
    public void setAuthToken(String token) {
        authToken = token;
    }
}
