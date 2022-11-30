package com.utotech.iper.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReceiverSentences extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent servIntent = new Intent(context, SentenceHandlerService.class);
        context.startService(servIntent);
    }
}
