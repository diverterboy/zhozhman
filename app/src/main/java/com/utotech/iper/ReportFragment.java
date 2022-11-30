package com.utotech.iper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utotech.iper.Adapter.RecyclerPracticeResultAdapter;
import com.utotech.iper.Database.PracticeDatabase;
import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;

import com.utotech.iper.Utils.Tools;

import java.util.List;

import calendar.PersianDate;

public class ReportFragment extends Fragment {
    RecyclerView recyclerReports;
    int yearNum;
    int monthNum;
    int dayNum;
    Tools tools;
    PracticeDatabase practiceDatabase;
    TextView textView;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("مشاهده گزارش");

        monthNum = getArguments().getInt("monthNumber");
        dayNum = getArguments().getInt("dayNumber");
        yearNum = new PersianDate().getYear();
        recyclerReports = view.findViewById(R.id.recyclerReports);
        practiceDatabase = PracticeDatabase.getInstance(getContext());
        tools = new Tools();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        practiceDatabase.idaoNight().getNightPracticeList();

        MorningPractice morningPractice = practiceDatabase.idao().getSpecificPractice(dayNum, monthNum, yearNum);
        NightPractice nightPractice = practiceDatabase.idaoNight().getSpecificPractice(dayNum, monthNum, yearNum);

        List<String> answers = tools.extractPractices(morningPractice, nightPractice);

        RecyclerPracticeResultAdapter adapter = new RecyclerPracticeResultAdapter(getContext(), monthNum, answers);
        recyclerReports.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }
}