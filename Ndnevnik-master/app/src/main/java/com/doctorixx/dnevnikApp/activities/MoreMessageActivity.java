package com.doctorixx.dnevnikApp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doctorixx.dnevnikApp.singeltons.Messager;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;
import com.doctroixx.NDnevnik.R;

public class MoreMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_message);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Message message = Messager.getInstance().getMessage();
        if (message == null) return;
        ((TextView) findViewById(R.id.date)).setText(message.getDate());
        ((TextView) findViewById(R.id.theme)).setText(message.getTheme());
        ((TextView) findViewById(R.id.message)).setText(message.getBody());
        ((TextView) findViewById(R.id.messageFrom)).setText(String.format(
                "Сообщение от %s", message.getUser().getName())
        );

        System.out.println();

    }
}