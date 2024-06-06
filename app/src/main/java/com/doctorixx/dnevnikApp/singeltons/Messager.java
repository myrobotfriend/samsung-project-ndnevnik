package com.doctorixx.dnevnikApp.singeltons;

import com.doctorixx.easyDnevnik.stuctures.messages.Message;

public class Messager {

    private static Messager instance;

    private Message message;

    private Messager() {
    }

    public static Messager getInstance() {
        if (instance == null) instance = new Messager();
        return instance;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
