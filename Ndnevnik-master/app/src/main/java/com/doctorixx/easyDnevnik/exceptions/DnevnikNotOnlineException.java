package com.doctorixx.easyDnevnik.exceptions;

public class DnevnikNotOnlineException extends DnevnikConnectException {

    public DnevnikNotOnlineException(){
        super();
    }

    public DnevnikNotOnlineException(String msg){
        super(msg);
    }

}
