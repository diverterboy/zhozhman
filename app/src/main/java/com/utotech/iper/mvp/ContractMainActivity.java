package com.utotech.iper.mvp;

import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;

public interface ContractMainActivity {

    interface View {
        void initial();
        void setupNotif();
        void navigationSwitch(int id);
        void btnsOnClick(ImageView btnNavMenu , ImageView btn_setting , ImageView btnHelp);
    }

    interface Presenter {
        void sendNotification(int hour, int minute, int requsetCode);
        void setupNotif();
        void switchCaseNavView(int id);
        void btnsOnClick(ImageView btnNavMenu , ImageView btn_setting , ImageView btnHelp);
    }
}
