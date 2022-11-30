package com.utotech.iper.mvp;

import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;
import com.utotech.iper.Model.Sentence;

import java.util.List;

public interface IModel {
    List<Sentence> getShownSentences();
    NightPractice getSpecificNightPractice(int dayOfMonth, int month, int year);
    MorningPractice getSpecificMorningPractice(int dayOfMonth, int month, int year);
    void playClick();
}
