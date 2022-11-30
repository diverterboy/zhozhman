package com.utotech.iper.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.utotech.iper.MainActivity;
import com.utotech.iper.R;

public class TeslaRuleFragment extends Fragment {
    TextView txtTitle1, txtTitle2, txtContent1, txtContent2, textView;

    // todo to be removed
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tesla_rule, container, false);
        initial(view);

        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("قانون جذب تسلا");

        return view;
    }

    private void initial(View view) {
        txtTitle1 = view.findViewById(R.id.txtTitle1);
        txtTitle2 = view.findViewById(R.id.txtTitle2);
        txtContent1 = view.findViewById(R.id.txtContent1);
        txtContent2 = view.findViewById(R.id.txtContent2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }
}