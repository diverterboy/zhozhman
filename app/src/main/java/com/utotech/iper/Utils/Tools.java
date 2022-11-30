package com.utotech.iper.Utils;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utotech.iper.Database.QsentenceDbAdapter;
import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;
import com.utotech.iper.Model.Sentence;
import com.utotech.iper.R;

import java.util.ArrayList;
import java.util.List;

import calendar.PersianDate;

public class Tools {
    QsentenceDbAdapter qsentenceDbAdapter;

    public com.utotech.iper.Model.Date getDate() {
        PersianDate date = new PersianDate();
        com.utotech.iper.Model.Date date1 = new com.utotech.iper.Model.Date();
        date1.setDayOfMonth(date.getDayOfMonth());
        date1.setMonthOfYear(date.getMonth());
        date1.setYear(date.getYear());
        return date1;
    }


    public String getToday() {
        PersianDate date = new PersianDate();
        String dayOfWeek = getDayOfWeek(date.getDayOfWeek());
        String dayOfMonth = String.valueOf(date.getDayOfMonth());
        String month = date.getMonthName();
        String year = String.valueOf(date.getYear());
        return dayOfWeek.concat(" ").concat(dayOfMonth).concat(" ").concat(month)
                .concat(" ").concat(year);
    }

    private String getDayOfWeek(int day) {
        String result;
        switch (day) {
            case 1:
                result = "جمعه";
                break;
            case 2:
                result = "شنبه";
                break;
            case 3:
                result = "یکشنبه";
                break;
            case 4:
                result = "دوشنبه";
                break;
            case 5:
                result = "سه شنبه";
                break;
            case 6:
                result = "چهارشنبه";
                break;
            case 7:
                result = "پنجشنبه";
                break;
            default:
                result = "not a problem";
        }
        return result;
    }

    public int getMonth() {
        PersianDate date = new PersianDate();
        return date.getMonth();
    }

    public String getNumericDate() {
        PersianDate date = new PersianDate();

        return date.getYear() + " / ".concat(date.getMonth() + " /").concat(date.getDayOfMonth() + "");
    }


    public void getQSentences(List<Sentence> sentenceList , Context context) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < sentenceList.size(); i++) {
            for (int in = 0; in < sentenceList.get(i).getRepeatNum(); in++) {
                strings.add(sentenceList.get(i).getSentence());
            }
        }

        qsentenceDbAdapter = new QsentenceDbAdapter(context);
        qsentenceDbAdapter.onGetQsentenced(strings);
    }

    public void timerLoop(Context context, List<Sentence> sentenceList, IQuestionDialogCallBack callBack) {
        qsentenceDbAdapter = new QsentenceDbAdapter(context);
        if (/*Shared.qSentences == null*/ qsentenceDbAdapter.isEmpty()) {
            getQSentences(sentenceList,context); // to fill Shared.qSentences

            // and we fill the pref index in database method

        } else {
            int index;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popup_window,
                        null);
                RelativeLayout relativeLayout = (RelativeLayout) layout.findViewById(R.id.rootLayout);
                TextView text = (TextView) layout.findViewById(R.id.titleText);

                index = Integer.parseInt(Shared.readAppSetting(context, "qSentenceIndex"));
                text.setText(/*Shared.qSentences.get(0)*/ qsentenceDbAdapter.getSentence(index));

                text.setTextSize(Float.parseFloat(Shared.readAppSetting(context, "fontToastySize")));
                text.setTextColor(Color.parseColor("#" + Shared.readAppSetting(context, "toastyTxtTheme")));

                //set font
                String fontName = Shared.readAppSetting(context, "fontToastyName");
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/" + fontName);
                text.setTypeface(typeface);
                relativeLayout.setBackgroundColor(Color.parseColor("#" + Shared.readAppSetting(context, "toastyTheme")));


                Toast toast = new Toast(context);
                toast.setGravity(Gravity.TOP | Gravity.END, 30, 30);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            } else {
                Window window = new Window(context);
                window.open();
            }

            index = Integer.parseInt(Shared.readAppSetting(context, "qSentenceIndex"));
            //Shared.qSentences.remove(0);
            qsentenceDbAdapter.deleteLastItem(index);
            Shared.writeAppSetting(context, "qSentenceIndex", String.valueOf(index + 1));



            if (/*Shared.qSentences.isEmpty()*/ qsentenceDbAdapter.isEmpty()) {

                //    Shared.qSentences = null;
                Shared.writeAppSetting(context, "appIsActivated", "off");
                callBack.onSuccess();
            }
        }

    }


    public List<String> extractPractices(MorningPractice morningPractice, NightPractice nightPractice) {
        List<String> listMorning = new ArrayList<>();
        listMorning.add(morningPractice.getThanks_god());
        listMorning.add(morningPractice.getWho_thanks_god());
        listMorning.add(morningPractice.getMy_year_goal());
        listMorning.add(morningPractice.getWhat_do_today());
        listMorning.add(morningPractice.getThree_top());
        listMorning.add(morningPractice.getWhat_god_does());

        List<String> listNight = new ArrayList<>();
        listNight.add(nightPractice.getDid_i_done());
        listNight.add(nightPractice.getWhat_for_tomorrow());
        listNight.add(nightPractice.getOne_to_hundred());
        listNight.add(nightPractice.getHow_tomorrow_better());
        listNight.add(nightPractice.getWhat_nice_today());
        listNight.add(nightPractice.getWhat_idea());
        listNight.add(nightPractice.getThankGod());

        List<String> result = new ArrayList<>();
        result.addAll(listMorning);
        result.addAll(listNight);

        return result;
    }


}
