package com.utotech.iper.Utils;

import com.utotech.iper.Model.Sentence;

import java.util.List;

public interface IDialogCallback {
    void onItemUpdated(List<Sentence> sentenceList);

}
