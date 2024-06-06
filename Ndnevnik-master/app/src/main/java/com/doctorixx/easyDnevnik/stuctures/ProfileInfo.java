package com.doctorixx.easyDnevnik.stuctures;

public class ProfileInfo {

    private final String name;
    private final String surname;
    private final String schoolName;
    private final EducationClass educationClass;

    public ProfileInfo(String name, String surname, String schoolName, EducationClass educationClass) {
        this.name = name;
        this.surname = surname;
        this.schoolName = schoolName;
        this.educationClass = educationClass;
    }

    public String getFullName(){
        return name + " " + surname;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public EducationClass getEducationClass() {
        return educationClass;
    }

}
