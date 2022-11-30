package com.utotech.iper;


import android.os.Bundle;
import androidx.cardview.widget.CardView;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.androchef.happytimer.countdowntimer.CircularCountDownView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.utotech.iper.mvp.ContractPracticeFragment;
import com.utotech.iper.mvp.PresenterPracticeFragment;

public class PracticeFragment extends Fragment implements ContractPracticeFragment.View {
    String status;
    TextView txtPractice, txtDate, textView;
    ImageView btnEdtPractice, btnReset;
    TextInputEditText edt_answer;
    MaterialButton btnGoToNext;
    CircularCountDownView circularCountDownView;
    FancyGifDialog.Builder dialog;
    CardView cardView;
    ContractPracticeFragment.Presenter presenter;

    public PracticeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_practice, container, false);
        initial(view);
        addResetBtn();
        initToolbarText();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.iniPracticeList();
        configDialog();
        configCircularCountDown();
        configTexts();
        onFirstTimeInPage();
        btnS();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
        MainActivity.toolbar.removeView(btnReset);
    }

    private void initial(View view) {
        btnReset = new ImageView(getContext());
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        status = getArguments().getString("key");
        txtPractice = view.findViewById(R.id.txtPractice);
        edt_answer = view.findViewById(R.id.edt_answer);
        btnGoToNext = view.findViewById(R.id.btnGoToNext);
        txtDate = view.findViewById(R.id.txtDate);
        circularCountDownView = view.findViewById(R.id.circularCountDownView);
        dialog = new FancyGifDialog.Builder(getContext());
        cardView = view.findViewById(R.id.cardview);
        btnEdtPractice = view.findViewById(R.id.btnEdtPractice);
        presenter = new PresenterPracticeFragment(getContext(), status, dialog, circularCountDownView, txtPractice, edt_answer, cardView);
    }


    @Override
    public void configTexts() {
        presenter.setFont(txtPractice);
        presenter.setFontSize(txtPractice);
    }

    @Override
    public void initToolbarText() {
        presenter.initToolbarText(textView);
    }

    @Override
    public void addResetBtn() { presenter.addResetBtn(btnReset);
    }

    @Override
    public void configDialog() {
        presenter.configDialog(this);
    }

    @Override
    public void configCircularCountDown() {
        presenter.configCircularCountDown();
    }


    @Override
    public void btnS() {
        btnGoToNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.btnGoToNextClicked(btnGoToNext, requireActivity());
            }
        });

        btnEdtPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.btnEdtPracticeClicked(getActivity());
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.btnResetClicked();
            }
        });
    }

    @Override
    public void onFirstTimeInPage() {
        presenter.onFirstTimeInPage(txtDate);
    }

}