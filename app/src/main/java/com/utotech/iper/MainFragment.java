package com.utotech.iper;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.utotech.iper.Database.DatabaseAccess;
import com.utotech.iper.Database.QsentenceDbAdapter;
import com.utotech.iper.mvp.ContractMainFragment;
import com.utotech.iper.mvp.Model;
import com.utotech.iper.mvp.PresenterMainFragment;

public class MainFragment extends Fragment implements ContractMainFragment.View {
    TextView txtDate;
    MaterialButton morningPractice, nightPractice, sentences, diary, activateApp;
    ContractMainFragment.Presenter presenter;
    NavController controller;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initial(view);

        morningPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPracticeMorningClicked();
            }
        });

        nightPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPracticeNightClicked();
            }
        });

        sentences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSentenceBtnClicked();
            }
        });

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onReportBtnClicked();
            }
        });

        activateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnActiveClicked();
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        onCreate();
    }

    @Override
    public void initial(View view) {
        controller = NavHostFragment.findNavController(this);
        presenter = new PresenterMainFragment(this, new Model(getContext()), getContext(), controller);
        txtDate = view.findViewById(R.id.txtDate);
        morningPractice = view.findViewById(R.id.morningPractice);
        nightPractice = view.findViewById(R.id.nightPractice);
        sentences = view.findViewById(R.id.sentences);
        diary = view.findViewById(R.id.diary);
        activateApp = view.findViewById(R.id.activateApp);
    }

    @Override
    public void onBtnActiveClicked() {
        presenter.playClick();
        presenter.checkOverlayPermission();
        QsentenceDbAdapter adapter = new QsentenceDbAdapter(getContext());
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());

        if (adapter.isEmpty()) {
            if (databaseAccess.getShownSentences().isEmpty()) {
                Toast.makeText(getContext(), "شما هنوز جمله انگیزشی برای پخش انتخاب نکرده اید", Toast.LENGTH_SHORT).show();
            } else {
                // fill qsentence
                presenter.fillQSentences();
                presenter.activationApp(false, activateApp);
            }
        } else {
            presenter.activationApp(false, activateApp);
        }

        /* if (*//*Shared.qSentences.size() == 0 *//* adapter.isEmpty()) {
        } else {
            presenter.fillQSentences();
            pr+esenter.activationApp(false, activateApp);
        }*/
    }

    @Override
    public void onPracticeNightClicked() {
        presenter.onPracticeNightClicked();
    }

    @Override
    public void onPracticeMorningClicked() {
        presenter.onPracticeMorningClicked();
    }

    @Override
    public void onCreate() {
        presenter.setTimeTextView(txtDate); // to set text to date textview
        presenter.activationApp(true, activateApp); // to check sentenceServiceActiveOrNot


    }
}