package com.doctorixx.easyDnevnik.exceptions;

public class DnevnikNoInternetConnection extends DnevnikConnectException
{

    public DnevnikNoInternetConnection(){
        super();
    }

    public DnevnikNoInternetConnection(String msg){
        super(msg);
    }

}
