package com.doctorixx.dnevnikApp.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;

public class ResumableAppCompatActiviy extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        DnevnikAction.getInstance(getApplicationContext());
    }
}
