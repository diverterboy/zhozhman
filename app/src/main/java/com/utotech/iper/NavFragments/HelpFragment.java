package com.utotech.iper.NavFragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.utotech.iper.MainActivity;
import com.utotech.iper.R;
import com.utotech.iper.Utils.AppHelpAdd;


public class HelpFragment extends Fragment {

    TextView txtHelp, textView;
    MaterialButton btnMedia;
    MediaPlayer player;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        txtHelp = view.findViewById(R.id.txtHelp);
        btnMedia = view.findViewById(R.id.btnMedia);
        player = MediaPlayer.create(getContext(), R.raw.help);
        AppHelpAdd helpAdd = new AppHelpAdd();
        helpAdd.addTheText(txtHelp);

        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("راهنمای برنامه");


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        btnMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnMedia.getText().toString().equals("پخش توضیحات")) {
                    player.start();
                    btnMedia.setText("توقف پخش");

                } else {
                    player.stop();
                    player= MediaPlayer.create(getContext(), R.raw.help);
                    btnMedia.setText("پخش توضیحات");
                }
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnMedia.setText("پخش توضیحات");
                player= MediaPlayer.create(getContext(), R.raw.help);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
        player.stop();

    }
}