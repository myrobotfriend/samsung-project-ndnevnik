package com.doctorixx.dnevnikApp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.doctorixx.dnevnikApp.singeltons.containers.Announmenter;
import com.doctorixx.easyDnevnik.stuctures.Announcement;
import com.doctroixx.NDnevnik.R;

public class MoreAnnouncementActivity extends AppCompatActivity {

    private Announcement announcement;

    private TextView text, header, theme, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tryGetArguments();
        setContentView(R.layout.activity_more_announcement_fragment);

        text = findViewById(R.id.text);
        theme = findViewById(R.id.theme);
        header = findViewById(R.id.other);
        date = findViewById(R.id.date);
    }

    @Override
    protected void onStart() {
        super.onStart();
        text.setText(announcement.getMessage());
        theme.setText(announcement.getTitle());
        header.setText(announcement.getHeader());
        date.setText(announcement.getDate());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("announcement", announcement);
    }

    private void tryGetArguments() {
        announcement = Announmenter.getInstance().getAnnouncement();
    }
}