package com.doctorixx.dnevnikApp.exceptions;


public class UnknownGradeException extends Exception {


    public UnknownGradeException(){
        super();
    }

    public UnknownGradeException(String grade){
        super(grade);
    }
}
