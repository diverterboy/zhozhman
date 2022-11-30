package com.utotech.iper.mvp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.utotech.iper.Service.ReceiverNotification;
import com.utotech.iper.Utils.Dialog;
import com.utotech.iper.Utils.IQuestionDialogCallBack;
import com.utotech.iper.Utils.Shared;

import java.util.Calendar;


public class PresenterSettingFragment implements ContractSettingFragment.Presenter {
    private final IModel model;
    private final Context context;
    Dialog dialog;

    public PresenterSettingFragment(Context context, Activity activity) {
        model = new Model(context);
        this.context = context;
        dialog = new Dialog(activity);
    }

    @Override
    public void setSize(String fontSize, SeekBar seekBar, TextView txtFontSizeNum) {
        seekBar.setProgress(Integer.parseInt(fontSize));
        txtFontSizeNum.setText(fontSize);
    }

    @Override
    public void setSwitches(SwitchCompat switchNotification, SwitchCompat switchSound) {
        if (Shared.readAppSetting(context, "clickSound").equals("on")) {
            switchSound.setChecked(true);
        }

        if (Shared.readAppSetting(context, "notification").equals("on")) {
            switchNotification.setChecked(true);
        }
    }

    @Override
    public void sendNotification(int requsetCode, int hour, int minute) {

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
        pendingIntent = PendingIntent.getBroadcast(context, requsetCode, intent, PendingIntent.FLAG_IMMUTABLE);

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
    public void cancelNotification(int requestCode) {
        Intent intent = new Intent(context, ReceiverNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    @Override
    public void dialogWhenClicked(View view) {
        dialog.whenToDialog(new IQuestionDialogCallBack() {
            @Override
            public void onSuccess() {
                Snackbar.make(view, "فاصله زمانی مورد نظر شما انتخاب شد", Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void btnThemeToasty(View view) {
        ColorPickerDialogBuilder
                .with(context)
                .setTitle("رنگ مورد نظر را انتخاب کنید")
                .initialColor(Color.parseColor("#" + Shared.readAppSetting(context, "toastyTheme")))
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(10).showColorPreview(true)
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        Shared.writeAppSetting(context, "toastyTheme", Integer.toHexString(selectedColor));
                        Snackbar.make(view, "رنگ بکگراند مورد نظر شما اعمال شد", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .build()
                .show();

    }

    @Override
    public void btnTxtColorToasty(View view) {
        ColorPickerDialogBuilder
                .with(context)
                .setTitle("رنگ مورد نظر را انتخاب کنید")
                .initialColor(Color.parseColor("#" + Shared.readAppSetting(context, "toastyTxtTheme")))
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(10).showColorPreview(true)
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        Shared.writeAppSetting(context, "toastyTxtTheme", Integer.toHexString(selectedColor));
                        Snackbar.make(view, "رنگ متن مورد نظر شما اعمال شد", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .build()
                .show();
    }

    @Override
    public void openFontDialog() {
        dialog.fontDialog();
    }

    @Override
    public void btns(MaterialButton btnFont, MaterialButton btnThemeToasty, MaterialButton btnTxtColor, MaterialButton btnDialogWhen ,SwitchCompat sound , SwitchCompat notification ) {
        btnFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFontDialog();
            }
        });
        btnTxtColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTxtColorToasty(view);
            }
        });
        btnThemeToasty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnThemeToasty(view);

            }
        });
        btnDialogWhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogWhenClicked(view);
            }
        });

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchSounds(b);
            }
        });
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchNotification(b);
            }
        });



    }

    @Override
    public void switchNotification(boolean b) {
        final int REQUEST_MORNING = 0;
        final int REQUEST_NIGHT = 1;
        if (b) {
            Shared.writeAppSetting(context, "notification", "on");
            sendNotification(22, 55, REQUEST_NIGHT);
            sendNotification(6, 0, REQUEST_MORNING);
        } else {
            Shared.writeAppSetting(context, "notification", "off");
            cancelNotification(REQUEST_MORNING);
            cancelNotification(REQUEST_NIGHT);
        }
    }

    @Override
    public void switchSounds(boolean b) {
        if (b) {
            Shared.writeAppSetting(context, "clickSound", "on");
        } else {
            Shared.writeAppSetting(context, "clickSound", "off");

        }
    }
}
