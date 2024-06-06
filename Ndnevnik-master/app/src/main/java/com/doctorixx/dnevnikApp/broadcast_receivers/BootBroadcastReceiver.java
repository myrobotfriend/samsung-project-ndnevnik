package com.doctorixx.dnevnikApp.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.doctorixx.dnevnikApp.works.WorkStarter;

public class BootBroadcastReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WorkStarter.activateWorks(context);
        Log.d("BOOT", "Booted");
    }
}
