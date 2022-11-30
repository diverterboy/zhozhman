package com.utotech.iper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utotech.iper.mvp.ContractDiaryFragment;
import com.utotech.iper.mvp.PresenterDiaryFragment;

public class DiaryFragment extends Fragment implements ContractDiaryFragment.View {
    RecyclerView recyclerDays;
    TextView txtMonthName, textView;
    ContractDiaryFragment.Presenter presenter;

    public DiaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        initial(view);
        setUp();
        return view;
    }

    @Override
    public void initial(View view) {
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("مشاهده گزارش");
        recyclerDays = view.findViewById(R.id.recyclerDays);
        txtMonthName = view.findViewById(R.id.txtMonthName);
        presenter = new PresenterDiaryFragment(getContext());
    }

    @Override
    public String checkMonth(int monthNumber) {
        return presenter.checkMonth(monthNumber);
    }

    @Override
    public void setUp() {
        int monthNumber = (int) getArguments().get("monthNumber");
        txtMonthName.setText(checkMonth(monthNumber));
        presenter.runRecyclerDays(monthNumber, this, recyclerDays);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }
}