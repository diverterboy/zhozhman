package com.utotech.iper.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Build;

import android.os.Handler;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;


import androidx.cardview.widget.CardView;

import com.utotech.iper.Database.QsentenceDbAdapter;
import com.utotech.iper.R;

import java.util.Timer;

public class Window {
    private Context context;

    private View mView;
    private TextView titleText;
    private CardView cardView;
    private RelativeLayout rootLayout;
    private WindowManager.LayoutParams mParams;

    private WindowManager mWindowManager;

    private LayoutInflater layoutInflater;

    QsentenceDbAdapter qsentenceDbAdapter;

    public Window(Context context) {
        qsentenceDbAdapter = new QsentenceDbAdapter(context);

        this.context = context;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            // set the layout parameters of the window

            mParams = new WindowManager.LayoutParams(

                    // Shrink the window to wrap the content rather

                    // than filling the screen

                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,

                    // Display it on top of other application windows

                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,

                    // Don't let it grab the input focus

                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                    // Make the underlying application window visible

                    // through any transparent parts

                    PixelFormat.TRANSLUCENT);


        }

        // getting a LayoutInflater

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflating the view with the custom layout we created

        mView = layoutInflater.inflate(R.layout.popup_window, null);

        // set onClickListener on the remove button, which removes

        // the view from the window
        titleText = mView.findViewById(R.id.titleText);
        cardView = mView.findViewById(R.id.cardView);
        rootLayout = mView.findViewById(R.id.rootLayout);
        titleText.setText( qsentenceDbAdapter.getSentence(Integer.parseInt(Shared.readAppSetting(context,"qSentenceIndex" ))));
        titleText.setTextSize(Float.parseFloat(Shared.readAppSetting(context, "fontToastySize")));
        titleText.setTextColor(Color.parseColor("#" + Shared.readAppSetting(context, "toastyTxtTheme")));

        //set font
        String fontName = Shared.readAppSetting(context, "fontToastyName");
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/" + fontName);
        titleText.setTypeface(typeface);
        cardView.setCardBackgroundColor(Color.parseColor("#" + Shared.readAppSetting(context, "toastyTheme")));

        // Define the position of the
        // window within the screen
        mParams.gravity = Gravity.TOP | Gravity.END;
        mParams.y = 30;
        mParams.x = 30;
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                close();
            }
        }, 12000);   //12 seconds

    }


    public void open() {


        try {

            // check if the view is already

            // inflated or present in the window

            if (mView.getWindowToken() == null) {

                if (mView.getParent() == null) {

                    mWindowManager.addView(mView, mParams);

                }

            }

        } catch (Exception e) {

            Log.d("Error1", e.toString());

        }


    }


    public void close() {


        try {

            // remove the view from the window

            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(mView);

            // invalidate the view

            mView.invalidate();

            // remove all views

            ((ViewGroup) mView.getParent()).removeAllViews();


            // the above steps are necessary when you are adding and removing

            // the view simultaneously, it might give some exceptions

        } catch (Exception e) {

            Log.d("Error2", e.toString());

        }

    }
}