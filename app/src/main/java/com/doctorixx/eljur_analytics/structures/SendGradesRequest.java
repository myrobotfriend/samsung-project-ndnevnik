package com.doctorixx.eljur_analytics.structures;

import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.eljur_analytics.Request;

import java.util.List;
import java.util.Map;

public class SendGradesRequest extends Request {
    private String auth_token;
    private Map<String, List<Grade>> grades;

    public SendGradesRequest(String auth_token, Map<String, List<Grade>> grades) {
        this.auth_token = auth_token;
        this.grades = grades;
    }
}
