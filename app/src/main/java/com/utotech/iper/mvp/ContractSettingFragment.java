package com.utotech.iper.mvp;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.button.MaterialButton;

public interface ContractSettingFragment {
    interface View {
        void initial(android.view.View view);

        void setSize(String fontSize);

        void setSwitches();

        void cancelNotification(int requestCode);

        void sendNotification(int hour, int minute, int requsetCode);
        void btns();

    }

    interface Presenter {
        void setSize(String fontSize, SeekBar seekBar, TextView txtFontSizeNum);

        void setSwitches(SwitchCompat switchNotification, SwitchCompat switchSound);

        void sendNotification(int requsetCode, int hour, int minute);

        void cancelNotification(int requestCode);

        void dialogWhenClicked(android.view.View view);

        void btnThemeToasty(android.view.View view);

        void btnTxtColorToasty(android.view.View view);

        void openFontDialog();

        void btns(MaterialButton btnFont, MaterialButton btnThemeToasty, MaterialButton btnTxtColor, MaterialButton btnDialogWhen, SwitchCompat sound , SwitchCompat notification);

        void switchNotification(boolean b);
        void switchSounds(boolean b);
    }
}
