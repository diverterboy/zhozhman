package com.utotech.iper.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utotech.iper.MainActivity;
import com.utotech.iper.R;

public class SorucesFragment extends Fragment {
    TextView textView, txt_manabe;


    public SorucesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_soruces, container, false);
        txt_manabe = view.findViewById(R.id.txt_manabe);
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("منابع و مواخذ");
        txt_manabe.setText("استفاده شده از کتابها و آموزه های اساتید بزرگ ایران وجهان، از جمله کتاب: برنامه اهداف نهایی، اثر برایان تریسی، و کتاب : رویاهایی که رویا نیستند، اثر استاد عباسمنش، و کتاب  اثرمرکب : دارن هاردی،  وسایر اساتید از کتابهایشان و آموزه هایشان استفاده شده که به نام برخی از آنان اکتفا میکنیم  : \n" +
                "➖ برایان ترسی\n" +
                "➖کوین ترودو\n" +
                "➖باب پراکتور\n" +
                "➖رابرت کیوساکی\n" +
                "➖استر هیکس\n" +
                "➖آنتونی رابینز\n" +
                "➖جول اوستین\n" +
                "➖جک کنفیلد\n" +
                "➖وین دایر\n" +
                "➖ناپلئون هیل\n" +
                "➖جو دیسپنزا\n" +
                "و…\n");

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }
}