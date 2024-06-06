package com.doctorixx.eljur_analytics.structures;

import com.doctorixx.eljur_analytics.Response;

import java.util.Map;

public class GradesByDateResponse extends Response {
    private final Map<String, Integer> result;

    public GradesByDateResponse(Map<String, Integer> result) {
        this.result = result;
    }

    public Map<String, Integer> getResult() {
        return result;
    }
}
