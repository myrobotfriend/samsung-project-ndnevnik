package com.doctorixx.dnevnikApp.storage.containers;

import com.doctorixx.easyDnevnik.stuctures.Grade;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GradeContainer {

    private Map<String, List<Grade>> grades;

    public GradeContainer(Map<String, List<Grade>> grades) {
        this.grades = grades;
    }

    public Map<String, List<Grade>> getGrades() {
        return grades;
    }

    @Override
    public boolean equals(Object o) {
        GradeContainer that = (GradeContainer) o;
        return Objects.equals(grades, that.grades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grades);
    }
}
