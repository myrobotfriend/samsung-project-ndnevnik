package com.doctorixx.dnevnikApp.storage;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DataSerializer<T> {

    private final Class<T> type;
    private final Context context;
    private final Gson gson = new Gson();

    public DataSerializer(Class<T> type, Context context) {
        this.type = type;
        this.context = context;
    }

    public void saveData(T data, SerializationFilesNames filesName) {
        String jsonString = gson.toJson(data);

        try (FileOutputStream fileOutputStream = context.openFileOutput(filesName.toString(), Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T openData(SerializationFilesNames filesName) {
        try (FileInputStream fileInputStream = context.openFileInput(filesName.toString());
             InputStreamReader streamReader = new InputStreamReader(fileInputStream)) {

            Gson gson = new Gson();
            return gson.fromJson(streamReader, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
