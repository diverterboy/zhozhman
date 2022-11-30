package com.utotech.iper.mvp;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;


public interface ContractPracticeFragment {
    interface View {
        void onFirstTimeInPage();
        void configTexts();
        void initToolbarText();
        void addResetBtn();
        void configDialog();
        void configCircularCountDown();
        void btnS();

    }

    interface Presenter {
        void onFirstTimeInPage(TextView txtDate);
        void setFont(TextView txtPractice);

        void setFontSize(TextView txtPractice);

        void initToolbarText(TextView toolbarTxtView);

        void addResetBtn(ImageView btnReset);

        void configDialog(Fragment practiceFragment);
        void configCircularCountDown();
        void playHeroSound();
        void fillingAnswersOperationMorning(MaterialButton btnGoToNext, Activity requireActivity);
        void fillingAnswersOperationNight();
        void closeKeyboard(Activity requireActivity);
        void runAnimation(TextView tv);
        void initMorningPractice(int index, String answer);
        void initNightPractice(int index, String answer);
         void clickSound();
        void iniPracticeList();
        void btnEdtPracticeClicked(Activity activity);
        void btnResetClicked();
        void btnGoToNextClicked(MaterialButton btnGoToNext , Activity requireActivity);

    }
}
