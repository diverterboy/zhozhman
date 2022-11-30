package com.utotech.iper;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.utotech.iper.Utils.Dialog;

import com.utotech.iper.Utils.Shared;
import com.utotech.iper.mvp.ContractSettingFragment;
import com.utotech.iper.mvp.PresenterSettingFragment;


public class SettingFragment extends Fragment implements ContractSettingFragment.View {
    TextView textView, txtFontSizeNum;
    SwitchCompat switchSound, switchNotification;
    MaterialButton btnFont, btnThemeToasty, btnTxtColor, btnDialogWhen;
    SeekBar seekBar;
    Dialog dialog;
    ContractSettingFragment.Presenter presenter;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initial(view);
        setSwitches();
        btns();
        setSize(Shared.readAppSetting(getContext(), "fontSize"));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtFontSizeNum.setText(String.valueOf(i));
                Shared.writeAppSetting(getContext(), "fontSize", String.valueOf(i));
                Shared.writeAppSetting(getContext(), "fontToastySize", String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }

    @Override
    public void initial(View view) {
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("تنظیمات");
        switchSound = view.findViewById(R.id.switchSound);
        btnThemeToasty = view.findViewById(R.id.btnThemeToasty);
        txtFontSizeNum = view.findViewById(R.id.txtFontSizeNum);
        seekBar = view.findViewById(R.id.seekbar);
        btnFont = view.findViewById(R.id.btnFont);
        btnDialogWhen = view.findViewById(R.id.btnDialogWhen);
        switchNotification = view.findViewById(R.id.switchNotification);
        btnTxtColor = view.findViewById(R.id.btnTxtColor);
        dialog = new Dialog(getActivity());
        presenter = new PresenterSettingFragment(getContext(), getActivity());
    }

    @Override
    public void setSize(String fontSize) {
        presenter.setSize(fontSize, seekBar, txtFontSizeNum);
    }

    @Override
    public void setSwitches() {
        presenter.setSwitches(switchNotification, switchSound);
    }

    @Override
    public void cancelNotification(int requestCode) {
        presenter.cancelNotification(requestCode);
    }

    @Override
    public void sendNotification(int hour, int minute, int requsetCode) {
        presenter.sendNotification(requsetCode, hour, minute);

    }

    @Override
    public void btns() {
        presenter.btns(btnFont, btnThemeToasty, btnTxtColor, btnDialogWhen, switchSound, switchNotification);
    }
}