package com.doctorixx.easyDnevnik.stuctures;

import java.util.List;
import java.util.Map;

public class FinalGades {

    private final Map<String, List<Grade>> out;
    private final List<String> outHeaders;

    public FinalGades(Map<String, List<Grade>> out, List<String> outHeaders) {
        this.out = out;
        this.outHeaders = outHeaders;
    }

    public Map<String, List<Grade>> getOut() {
        return out;
    }

    public List<String> getOutHeaders() {
        return outHeaders;
    }
}
