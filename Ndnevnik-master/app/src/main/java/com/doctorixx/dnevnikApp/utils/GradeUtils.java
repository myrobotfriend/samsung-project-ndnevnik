package com.doctorixx.dnevnikApp.utils;

import com.doctorixx.easyDnevnik.stuctures.Grade;

import java.util.ArrayList;
import java.util.List;

public final class GradeUtils {

    public static String toNormalGradeFormat(Grade grade) {
        String gradeFormatted = grade
                .getStringGrade()
                .toUpperCase()
                .replace(" ", "")
                .replaceFirst("[0-9][X]", "") // For 1x 5
                .replaceFirst("[^ /+-][0-9]", ""); // For H x1

        if (gradeFormatted.length() == 2){
            final StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(gradeFormatted.charAt(0));
            stringBuilder.append('/');
            stringBuilder.append(gradeFormatted.charAt(0));

            return stringBuilder.toString();
        }

        return gradeFormatted;
    }

    public static String getGradeNormalString(Grade grade) {
        String stringGrade = grade.getStringGrade();


        //Ignore + and -
        String fixStringGrade = toNormalGradeFormat(grade)
                .replace("+", "")
                .replace("-", "");


        if (stringGrade.contains("x")) {
            return fixStringGrade;
        }

        //for grade like ( 5+ ; 3- )
        //if (fixStringGrade != stringGrade) return fixStringGrade;

        //For grade like 5/4
        if (stringGrade.contains("/")) {
            String[] grades = fixStringGrade.split("/");
            return String.valueOf(Math.round(avverageStringArray(grades)));

        }

        String stripped = stringGrade.trim();

        if (stripped.length() == 2) {

            String[] grades = {
                    String.valueOf(stripped.charAt(0)),
                    String.valueOf(stripped.charAt(1))
            };
            return String.valueOf(Math.round(avverageStringArray(grades)));

        }


        return formatPlusMinus(fixStringGrade.toUpperCase());
    }


    public static float averageGrades(List<Grade> grades) {
        List<Integer> countable = new ArrayList<>();

        for (Grade grade : grades) {

            try {
                String toParseInt = grade.getStringGrade()
                        .replace("+", "")
                        .replace("-", "");
                if (toParseInt.contains("/")) {
                    String[] m_grades = toParseInt.split("/");
                    for (String grade_temp : m_grades) {
                        String toParseInt_temp = grade_temp
                                .replace("+", "")
                                .replace("-", "");
                        countable.add(Integer.valueOf(toParseInt_temp));
                    }
                } else {
                    int result = Integer.parseInt(toParseInt);
                    countable.add(result);
                }
            } catch (Exception ignored) {

            }
        }

        float avverage = 0;
        if (countable.size() != 0) {

            for (int i : countable) {
                avverage += i;
            }
            avverage /= countable.size();
        }

        return avverage;
    }

    private static String formatPlusMinus(String string) {
        if (string.length() <= 1) return string;
        if (string.charAt(1) == '+' || string.charAt(1) == '-') {
            return String.copyValueOf(
                    new char[]{
                            string.charAt(1),
                            string.charAt(0)}
            );
        }
        return string;
    }

    private static float avverageStringArray(String[] arr) {
        if (arr.length == 0) return 0;

        int sumCorrect = 0;

        float result = 0;
        for (String elem : arr) {
            try {
                result += Integer.parseInt(elem);
            } catch (NumberFormatException e) {
                sumCorrect -= 1;
            }

        }
        result /= (arr.length + sumCorrect);

        return result;
    }

}
