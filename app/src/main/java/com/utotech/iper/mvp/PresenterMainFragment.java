package com.utotech.iper.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;

import com.google.android.material.button.MaterialButton;
import com.utotech.iper.Model.Date;
import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;
import com.utotech.iper.R;
import com.utotech.iper.Service.SentenceHandlerService;
import com.utotech.iper.Utils.Shared;
import com.utotech.iper.Utils.Tools;

public class PresenterMainFragment implements ContractMainFragment.Presenter {
    ContractMainFragment.View view;
    IModel model;
    Context context;
    Tools tools;
    NavController controller;

    public PresenterMainFragment(ContractMainFragment.View view, IModel model, Context context, NavController controller) {
        this.view = view;
        this.model = model;
        this.context = context;
        tools = new Tools();
        this.controller = controller;
    }

    @Override
    public boolean checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            if (!Settings.canDrawOverlays(context)) {

                // send user to the device settings

                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);

                context.startActivity(myIntent);
                Toast.makeText(context, "لطفا مجوز های لازم را به برنامه بدهید", Toast.LENGTH_LONG).show();

            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void activationApp(boolean areYouJustChecking, MaterialButton activateApp) {
        String status = Shared.readAppSetting(context, "appIsActivated"); // to check is active or not
        if (areYouJustChecking) {
            if (status.equals("on")) { // to change color of btn
                activateApp.setBackgroundColor(context.getResources().getColor(R.color.teal_200));

            } else {

                activateApp.setBackgroundColor(context.getResources().getColor(R.color.purple_200));
            }
        } else {
            // for onClick

            Intent intent = new Intent(context, SentenceHandlerService.class);
            if (status.equals("on")) {
                Shared.writeAppSetting(context, "appIsActivated", "off");
                context.stopService(intent);
            } else { // if its off already
                Shared.writeAppSetting(context, "appIsActivated", "on");
//.....
                intent.setAction("startService");
                context.startService(intent);
            }
            activationApp(true, activateApp); // to change color immediately
        }
    }

    @Override
    public void fillQSentences() {
        tools.getQSentences(model.getShownSentences(),context);

    }

    @Override
    public void onPracticeNightClicked() {
        model.playClick();
        Date date = tools.getDate();
        NightPractice practice = model.getSpecificNightPractice(date.getDayOfMonth(), date.getMonthOfYear(), date.getYear());
        if (practice != null) {
            Toast.makeText(context, "تمرین امروز شما ثبت شده است، برای مشاهده به بخش گزارش بروید", Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("key", "night");
            controller.navigate(R.id.action_mainFragment_to_practiceFragment, bundle);
        }
    }

    @Override
    public void onPracticeMorningClicked() {
        model.playClick();
        Date date = tools.getDate();
        MorningPractice practice = model.getSpecificMorningPractice(date.getDayOfMonth(), date.getMonthOfYear(), date.getYear());
        if (practice != null) {
            Toast.makeText(context, "تمرین امروز شما ثبت شده است، برای مشاهده به بخش گزارش بروید", Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("key", "morning");
            controller.navigate(R.id.action_mainFragment_to_practiceFragment, bundle);

        }

    }

    @Override
    public void onReportBtnClicked() {
        model.playClick();
        controller.navigate(R.id.action_mainFragment_to_diaryFragment);
    }

    @Override
    public void playClick() {
        model.playClick();
    }

    @Override
    public void setTimeTextView(TextView txtDate) {
        txtDate.setText(tools.getToday());

    }

    @Override
    public void onSentenceBtnClicked() {
        model.playClick();
        controller.navigate(R.id.action_mainFragment_to_sentenceFragment);
    }
}
