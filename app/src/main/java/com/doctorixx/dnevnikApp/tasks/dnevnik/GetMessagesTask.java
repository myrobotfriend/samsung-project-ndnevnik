package com.doctorixx.dnevnikApp.tasks.dnevnik;

import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.MessagesContainer;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnSuccess;
import com.doctorixx.easyDnevnik.Messageble;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;

import java.util.List;

public class GetMessagesTask extends AsyncTask<Void, Void, List<Message>> {

    private OnSuccess<List<Message>> onSuccess;

    public GetMessagesTask() {
    }

    public GetMessagesTask(OnSuccess<List<Message>> onSuccess) {
        this.onSuccess = onSuccess;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        List<Message> messages = openData();
        if (messages != null) onSuccess.run(messages);
    }

    @Override
    protected List<Message> doInBackground(Void... voids) {
        Messageble dnevnik = (Messageble) DnevnikAction.getInstance().getDnevnik();
        try {
            return dnevnik.getMessages();
        } catch (DnevnikNotOnlineException | DnevnikNoInternetConnection ignored) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Message> messages) {
        super.onPostExecute(messages);
        if (onSuccess != null && messages != null) {
            onSuccess.run(messages);
            saveData(messages);
        }
    }

    private void saveData(List<Message> messages) {
        new DataSerializer<>(MessagesContainer.class, DnevnikAction.getInstance().getContext())
                .saveData(
                        new MessagesContainer(messages),
                        SerializationFilesNames.MESSAGES_CONTAINER_PATH
                );
    }

    private List<Message> openData() {
        MessagesContainer container = new DataSerializer<>(
                MessagesContainer.class, DnevnikAction.getInstance().getContext())
                .openData(SerializationFilesNames.MESSAGES_CONTAINER_PATH);
        if (container != null) return container.getMessages();

        return null;
    }
}
