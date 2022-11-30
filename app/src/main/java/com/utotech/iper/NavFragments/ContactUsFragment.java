package com.utotech.iper.NavFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.utotech.iper.MainActivity;
import com.utotech.iper.R;


public class ContactUsFragment extends Fragment {

    TextView textView, txtCopyRight;
    MaterialButton btn_contactUS;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        init(view);

        txtCopyRight.setText("\uD83D\uDD34 ماهیچ اپلیکیشن مشابهی نداریم. \n" +
                "\uD83D\uDD34ما هیچ پیج اینستاگرام ویا سایتی نداریم، تنها راه ارتباطی با ما فقط شماره تماس 09213744652 ، و همین دکمه است .\n devloped by : mobin.developer");
        textView.setText("تماس با ما");

        btn_contactUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://t.me/p_servat";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }

    private void init(View view){
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        txtCopyRight = view.findViewById(R.id.txtCopyRight);
        btn_contactUS = view.findViewById(R.id.btn_contactUS);
    }
}