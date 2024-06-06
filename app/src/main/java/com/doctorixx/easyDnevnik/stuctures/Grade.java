package com.doctorixx.easyDnevnik.stuctures;

import java.util.Objects;

public class Grade {
    private final String grade;
    private final String date;
    private final Subject subject;
    private String period;

    public Grade(String grade, String date, Subject subject) {
        this.grade = grade;
        this.date = date;
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getStringGrade() {
        return grade;
    }

    public boolean isValid() {
        return !Objects.equals(grade, "") &&
                !Objects.equals(date, "") &&
                subject != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(grade, grade1.grade) && Objects.equals(date, grade1.date) && Objects.equals(subject, grade1.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, date, subject);
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
