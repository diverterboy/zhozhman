package com.utotech.iper.mvp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;

import com.utotech.iper.R;
import com.utotech.iper.Service.ReceiverNotification;
import com.utotech.iper.Utils.Shared;

import java.util.Calendar;

public class PresenterMainActivity implements ContractMainActivity.Presenter {

    IModel model;
    Context context;
    NavController controller;
    DrawerLayout drawerLayout;

    public PresenterMainActivity(IModel model, Context context, NavController controller, DrawerLayout drawerLayout) {
        this.model = model;
        this.context = context;
        this.controller = controller;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void sendNotification(int hour, int minute, int requsetCode) {
        Intent notifyIntent = new Intent(context, ReceiverNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (context, requsetCode, notifyIntent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        Calendar calendar = Calendar.getInstance();

        // calendar is called to get current time in hour and minute
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Intent intent = new Intent(context, ReceiverNotification.class);

        // we call broadcast using pendingIntent
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        long time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
        if (System.currentTimeMillis() > time) {
            // setting time as AM and PM
            if (Calendar.AM_PM == 0)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);
        }


        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,
                86400000, pendingIntent);

    }

    @Override
    public void setupNotif() {
        final int REQUEST_MORNING = 0;
        final int REQUEST_NIGHT = 1;
        String notifStatus = Shared.readAppSetting(context, "notification");
        if (notifStatus.equals("on")) {

            sendNotification(22, 55, REQUEST_NIGHT);
            sendNotification(6, 0, REQUEST_MORNING);
        }
    }

    @Override
    public void switchCaseNavView(int id) {
        switch (id) {
            case R.id.item_contactUs:
                controller.navigate(R.id.contactUsFragment);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            case R.id.item_mustBooks:
                controller.navigate(R.id.mustReadBooksFragment);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            case R.id.item_qanoonJazb:
                controller.navigate(R.id.jazbFragment);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            case R.id.item_qanoonJazbTesla:
                controller.navigate(R.id.teslaRuleFragment);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;

            case R.id.item_resources:
                controller.navigate(R.id.sorucesFragment);
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
        }

    }

    @Override
    public void btnsOnClick(ImageView btnNavMenu, ImageView btn_setting, ImageView btnHelp) {
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.settingFragment);
            }
        });
        btnNavMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
                model.playClick();
            }

        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.helpFragment);

            }
        });
    }
}
