package com.utotech.iper.mvp;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;

import com.google.android.material.button.MaterialButton;

public interface ContractMainFragment {

    interface View {
        void initial(android.view.View view);
        void onBtnActiveClicked();
        void onPracticeNightClicked();
        void onPracticeMorningClicked();
        void onCreate();

    }

    interface Presenter {
        boolean checkOverlayPermission();
        void activationApp(boolean areYouJustChecking , MaterialButton activateApp);
        void fillQSentences();

        void setTimeTextView(TextView txtDate);
        // brn
        void onSentenceBtnClicked();
        void onPracticeNightClicked();
        void onPracticeMorningClicked();
        void onReportBtnClicked();
        void playClick();
    }

}
