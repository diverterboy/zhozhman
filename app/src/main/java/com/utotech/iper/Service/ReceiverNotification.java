package com.utotech.iper.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReceiverNotification extends BroadcastReceiver {
    public ReceiverNotification() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1);
    }
}