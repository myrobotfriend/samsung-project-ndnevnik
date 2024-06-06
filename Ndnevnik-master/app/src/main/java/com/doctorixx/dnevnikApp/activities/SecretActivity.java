package com.doctorixx.dnevnikApp.activities;

import android.os.Bundle;

import com.doctroixx.NDnevnik.R;

public class SecretActivity extends ResumableAppCompatActiviy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            finish();
            System.out.println(5 / 0);
        }
        setContentView(R.layout.activity_secret);
    }
}