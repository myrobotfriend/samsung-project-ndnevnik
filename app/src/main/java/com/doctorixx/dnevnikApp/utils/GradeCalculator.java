package com.doctorixx.dnevnikApp.utils;

import com.doctorixx.easyDnevnik.stuctures.Grade;

import java.util.ArrayList;
import java.util.List;

public class GradeCalculator {

    private static final int DEFAULT_ITERATION_COUNT = 15;

    public static List<List<Grade>> calculateGrades(List<Grade> grades, float needGrade) {
        return calculateGrades(grades, needGrade, DEFAULT_ITERATION_COUNT);
    }


    public static List<List<Grade>> calculateGrades(List<Grade> grades, float needGrade, int iterations) {
        List<List<Grade>> out = new ArrayList<>();

        List<Grade> tempOut;
        if (needGrade > 4) {
            tempOut = addGradesToAverage(grades, 5, needGrade, iterations);
            if (tempOut != null) out.add(tempOut);
        } else if (needGrade > 3) {
            for (int i = 4; i <= 5; i++) {
                tempOut = addGradesToAverage(grades, i, needGrade, iterations);
                if (tempOut != null) out.add(tempOut);
            }
        } else if (needGrade > 2) {
            for (int i = 3; i <= 5; i++) {
                tempOut = addGradesToAverage(grades, i, needGrade, iterations);
                if (tempOut != null) out.add(tempOut);
            }
        }

        return out;
    }

    private static List<Grade> addGradesToAverage(List<Grade> grades, int grade, float needAverage, int iterations) {
        List<Grade> tempGrades = new ArrayList<>(grades);


        for (int i = 0; i < iterations; i++) {
            tempGrades.add(new Grade(String.valueOf(grade), null, null));
            float average = GradeUtils.averageGrades(tempGrades);
            if (average >= needAverage) break;
        }

        float average = GradeUtils.averageGrades(tempGrades);

        if (average < needAverage) return null;

        List<Grade> unique = new ArrayList<>();
        for (int i = grades.size(); i < tempGrades.size(); i++) {
            unique.add(tempGrades.get(i));
        }

        return unique;
    }

}
