package com.doctorixx.dnevnikApp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.doctorixx.changelog.ChangeLog;
import com.doctorixx.changelog.ChangeLogStorage;

public class ChangeLogDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(ChangeLog.getMsg())
                .setTitle(ChangeLog.getTitle())
                .setNegativeButton("Больше не показывть", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeLogStorage.put(getActivity().getApplicationContext());
                    }
                })
                .setNeutralButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }



}