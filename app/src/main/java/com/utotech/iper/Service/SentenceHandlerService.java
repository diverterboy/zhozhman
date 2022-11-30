package com.utotech.iper.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.utotech.iper.Database.DatabaseAccess;
import com.utotech.iper.Database.QsentenceDbAdapter;
import com.utotech.iper.R;
import com.utotech.iper.Utils.IQuestionDialogCallBack;
import com.utotech.iper.Utils.Shared;
import com.utotech.iper.Utils.Tools;
import com.utotech.iper.mvp.ContractMainFragment;

public class SentenceHandlerService extends Service {
    Context context;
    Handler handler;
    Runnable runnable;
    private final String CHANNEL_ID = "channelId";
    private NotificationManager notifManager;
    DatabaseAccess databaseAccess;
    Tools tools = new Tools();
    QsentenceDbAdapter qsentenceDbAdapter;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        databaseAccess = DatabaseAccess.getInstance(context);
        qsentenceDbAdapter = new QsentenceDbAdapter(context);
        runnable = new Runnable() {
            @Override
            public void run() {
                runLooper(databaseAccess, tools);
                handler.postDelayed(this, getWhenMilliSec());
                if ( qsentenceDbAdapter.isEmpty() ) {
                    Shared.writeAppSetting(context, "appIsActivated", "off");
                    handler.removeCallbacks(this);
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String offerChannelName = "Service Channel";
            String offerChannelDescription = "iper";
            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notifChannel = new NotificationChannel(CHANNEL_ID, offerChannelName, offerChannelImportance);
            notifChannel.setDescription(offerChannelDescription);
            notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);

        }
        Intent stopSelf = new Intent(this, SentenceHandlerService.class);
        stopSelf.setAction("stopSelf");
        PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ژوژمان")
                .setContentText("جملات انگیزشی روشن است")
                .addAction(new NotificationCompat.Action(R.drawable.ic_baseline_delete_24, " توقف", pStopSelf));


        Notification servNotification = sNotifBuilder.build();


        startForeground(1, servNotification);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("stopSelf")) {
            onDestroy();
            Shared.writeAppSetting(context, "appIsActivated", "off");
        }

        if ( qsentenceDbAdapter.isEmpty()) {
            tools.getQSentences(databaseAccess.getShownSentences(),context);
        }

        handler = new Handler();

        handler.postDelayed(runnable, getWhenMilliSec());   //calculate time


        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        stopSelf();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    private void runLooper(DatabaseAccess databaseAccess, Tools tools) {

        // if its off already
        //to run sentences
        tools.timerLoop(context, databaseAccess.getShownSentences(), new IQuestionDialogCallBack() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(context, ReceiverSentences.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 3, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();
                ContractMainFragment.Presenter presenter;
                onDestroy();
            }
        });
    }

    private int getWhenMilliSec() {

        String value = Shared.readAppSetting(context, "whenTo");
        switch (value) {
            case "1":
                return 3600000;
            case "5":
                return 300000;
            case "10":
                return 600000;
            case "20":
                return 1200000;
            case "30":
                return 1800000;
            case "45sec" :
                return 45000;
            default:
                return 0;
        }
    }

}
