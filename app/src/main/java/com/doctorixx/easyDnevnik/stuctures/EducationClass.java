package com.doctorixx.easyDnevnik.stuctures;

public class EducationClass {

    private final int classNumber;
    private final char classLetter;

    public EducationClass(int classNumber, char classLetter) {
        this.classNumber = classNumber;
        this.classLetter = classLetter;
    }


    public EducationClass(String className) {
        if (className == null){
            classLetter = ' ';
            classNumber = -1;
            return;
        }
        classLetter = className.charAt(className.length()-1);
        classNumber = Integer.parseInt(
                className.substring(0, className.length()-1)
        );
    }


    @Override
    public boolean equals(Object obj) {
        return (this.toString().equals(obj.toString()));
    }

    @Override
    public String toString() {
        return Integer.toString(classNumber) + Character.toString(classLetter);
    }

    public int getClassNumber(){
        return classNumber;
    }

    public String getClassLetterString(){
        return Character.toString(classLetter);
    }

    public char getClassLetter(){
        return classLetter;
    }

}

