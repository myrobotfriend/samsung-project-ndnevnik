package com.doctorixx.dnevnikApp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.doctorixx.dnevnikApp.singeltons.AccountManager;

public class ExitDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Вы уверены, что хотите выйти?")
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Activity activity = getActivity();
                        Context ctx = activity.getApplicationContext();

                        AccountManager.TrylogoutAndStartAuthActivity(ctx);

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
