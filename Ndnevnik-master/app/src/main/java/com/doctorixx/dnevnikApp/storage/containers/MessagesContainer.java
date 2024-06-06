package com.doctorixx.dnevnikApp.storage.containers;

import com.doctorixx.easyDnevnik.stuctures.messages.Message;

import java.util.List;

public class MessagesContainer {

    private final List<Message> messages;

    public MessagesContainer(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
