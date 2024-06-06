package com.doctorixx.easyDnevnik;

import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;

import java.util.List;

public interface Messageble {

    List<Message> getMessages() throws DnevnikNotOnlineException, DnevnikNoInternetConnection;
}
