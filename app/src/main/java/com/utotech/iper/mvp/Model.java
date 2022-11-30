package com.utotech.iper.mvp;

import android.content.Context;
import android.media.MediaPlayer;

import com.utotech.iper.Database.DatabaseAccess;
import com.utotech.iper.Database.PracticeDatabase;
import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;
import com.utotech.iper.Model.Sentence;
import com.utotech.iper.R;
import com.utotech.iper.Utils.Shared;

import java.util.List;

public class Model implements IModel {
    Context context;
    DatabaseAccess databaseAccess;
    PracticeDatabase practiceDatabase;

    public Model(Context context) {
        this.context = context;
        databaseAccess = DatabaseAccess.getInstance(context);
        practiceDatabase = PracticeDatabase.getInstance(context);
    }

    @Override
    public List<Sentence> getShownSentences() {
        return databaseAccess.getShownSentences();
    }

    @Override
    public NightPractice getSpecificNightPractice(int dayOfMonth, int month, int year) {
        return practiceDatabase.idaoNight().getSpecificPractice(dayOfMonth, month, year);
    }

    @Override
    public MorningPractice getSpecificMorningPractice(int dayOfMonth, int month, int year) {
        return practiceDatabase.idao().getSpecificPractice(dayOfMonth,month,year);
    }

    @Override
    public void playClick() {
        if (Shared.readAppSetting(context, "clickSound").equals("on")) {
            final MediaPlayer player = MediaPlayer.create(context, R.raw.tap_sound);
            player.start();
        }
    }

}
