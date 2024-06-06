package com.doctorixx.easyDnevnik.stuctures.messages;

public class Message {

    private final int id;
    private final String theme;
    private final String date;
    private final String body;
    private final MessageUser user;
    private final MessageStatus status;

    public Message(int id, String subject, String date, String body, MessageUser user, MessageStatus status) {
        this.id = id;
        this.theme = subject;
        this.date = date;
        this.body = body;
        this.user = user;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public MessageUser getUser() {
        return user;
    }

    public MessageStatus getStatus() {
        return status;
    }
}
