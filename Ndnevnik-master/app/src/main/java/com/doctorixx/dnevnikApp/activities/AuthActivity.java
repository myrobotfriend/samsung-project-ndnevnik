package com.doctorixx.dnevnikApp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doctorixx.dnevnikApp.other.AppState;
import com.doctorixx.dnevnikApp.other.AuthData;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.easyDnevnik.exceptions.DnevnikAuthException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikConnectException;
import com.doctorixx.easyDnevnik.instances.TestDnevnik;
import com.doctroixx.NDnevnik.R;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class AuthActivity extends ResumableAppCompatActiviy {

    private TextView login;
    private TextView password;

    private boolean taskRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_auth);

        login = findViewById(R.id.loginInput);
        password = findViewById(R.id.passwordInput);

        tryRestoreAccountData();

    }


    public void onClick(View view) {
        if (!taskRunning) {
            taskRunning = true;
            try {
                AuthData data = new AuthData(login.getText().toString(), password.getText().toString(), getApplicationContext());

                boolean testFlag = false;

                if (data.getLogin().equals("rustore TEST")) {
                    testFlag = true;
                    DnevnikAction.getInstance().setDnevnik(new TestDnevnik());
                } else {
                    DnevnikAction.getInstance().getDnevnik().auth(data.getLogin(), data.getPassword());

                }


                Toast toast = Toast.makeText(getApplicationContext(), "Успешный вход", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                if (testFlag) onSuccesAuth(data, AppState.TEST_NDEVNIK);
                else onSuccesAuth(data);

            } catch (DnevnikConnectException e) {
                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка подключения", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } catch (DnevnikAuthException e) {
                Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } catch (Exception e) {
                taskRunning = false;
            } finally {
                taskRunning = false;
            }
        }

    }

    private void onSuccesAuth(AuthData data) {
        data.put();
        AppState.put(AppState.NEED_SYNC, getApplicationContext());
        FirebaseCrashlytics.getInstance().setUserId(login.getText().toString());
        startActivity(new Intent(getApplicationContext(), LauncherActivity.class));
    }

    private void onSuccesAuth(AuthData data, AppState appState) {
        data.put();
        AppState.put(appState, getApplicationContext());
        FirebaseCrashlytics.getInstance().setUserId(login.getText().toString());
        startActivity(new Intent(getApplicationContext(), LauncherActivity.class));
    }

    private void tryRestoreAccountData() {
        AuthData authData = AuthData.get(getApplicationContext());
        if (authData != null) {
            login.setText(authData.getLogin());
            password.setText(authData.getPassword());
        }
    }

}