package com.doctorixx.dnevnikApp.storage.containers;

import com.doctorixx.easyDnevnik.stuctures.Grade;

import java.util.List;

public class LastGradesContainer {
    private List<Grade> grades;
    private List<Grade> nonReadGrades;

    public LastGradesContainer(List<Grade> grades, List<Grade> nonReadGrades) {
        this.grades = grades;
        this.nonReadGrades = nonReadGrades;
    }

    public List<Grade> getGrades() {
        return grades;
    }
}
