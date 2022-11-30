package com.utotech.iper.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.utotech.iper.MainActivity;
import com.utotech.iper.R;
import com.utotech.iper.Utils.Shared;

public class EssayShowFragment extends Fragment {
    ImageView imgEssay;
    TextView txtTitle, txtContent, textView;

    public EssayShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_essay_show, container, false);
        initial(view);
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("مقاله");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int index= getArguments().getInt("essayNumber");
        txtTitle.setText(Shared.titles.get(index));
        txtContent.setText(Shared.content.get(index));
        Picasso.get().load(Shared.essayImages.get(index)).into(imgEssay);


    }

    public void initial(View view){
        txtContent = view.findViewById(R.id.txtContent);
        txtTitle = view.findViewById(R.id.txtTitle);
        imgEssay = view.findViewById(R.id.imgEssay);
    }
}