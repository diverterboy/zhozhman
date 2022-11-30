package com.utotech.iper.mvp;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.androchef.happytimer.countdowntimer.CircularCountDownView;
import com.androchef.happytimer.countdowntimer.HappyTimer;
import com.google.android.material.button.MaterialButton;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.utotech.iper.Database.PracticeDatabase;
import com.utotech.iper.MainActivity;
import com.utotech.iper.Model.Date;
import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;
import com.utotech.iper.R;
import com.utotech.iper.Utils.Dialog;
import com.utotech.iper.Utils.IQuestionDialogCallBack;
import com.utotech.iper.Utils.Shared;
import com.utotech.iper.Utils.Tools;

import java.util.List;

public class PresenterPracticeFragment implements ContractPracticeFragment.Presenter {
    Context context;
    String status;
    FancyGifDialog.Builder dialog;
    IModel model;
    CircularCountDownView circularCountDownView;
    private List<String> practices;
    int i;
    Date date;
    MorningPractice morningPractice;
    NightPractice nightPractice;
    TextView txtPractice;
    EditText edt_answer;
    CardView cardView;
    Tools tools;

    public PresenterPracticeFragment(Context context, String status, FancyGifDialog.Builder dialog, CircularCountDownView circularCountDownView,
                                     TextView txtPractice, EditText edt_answer,
                                     CardView cardView) {
        this.context = context;
        this.status = status;
        this.dialog = dialog;
        this.circularCountDownView = circularCountDownView;
        this.txtPractice = txtPractice;
        this.edt_answer = edt_answer;
        this.cardView = cardView;
        morningPractice = new MorningPractice();
        nightPractice = new NightPractice();
        model = new Model(context);
        tools = new Tools();
        date = tools.getDate();
    }

    @Override
    public void onFirstTimeInPage(TextView txtDate) {
        txtDate.setText(tools.getToday());

        if (Shared.readShared(context, status, status).isEmpty()) {
            Shared.writeIndexPractice("0", context, status);
        }
        i = Integer.parseInt(Shared.readShared(context, status, status));
        txtPractice.setText(practices.get(i));
    }

    @Override
    public void setFont(TextView txtPractice) {
        String fontName = Shared.readAppSetting(context, "fontName");
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/" + fontName);
        txtPractice.setTypeface(typeface);
    }

    @Override
    public void setFontSize(TextView txtPractice) {
        int fontSize = Integer.parseInt(Shared.readAppSetting(context, "fontSize"));
        txtPractice.setTextSize(fontSize);
    }

    @Override
    public void initToolbarText(TextView toolbarTxtView) {
        if (status.equals("morning")) {
            toolbarTxtView.setText("تمرینات صبح");
        } else {
            toolbarTxtView.setText("تمرینات شب");
        }
    }

    @Override
    public void addResetBtn(ImageView btnReset) {
        btnReset.setLayoutParams(new ConstraintLayout.LayoutParams(60, 60));
        btnReset.setImageResource(R.drawable.ic_baseline_restore_24);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btnReset.setColorFilter(context.getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        }
        MainActivity.toolbar.addView(btnReset);
    }

    @Override
    public void configDialog(Fragment practiceFragment) {

        dialog.setTitle("تمرین تجسم");
        dialog.setMessage("تبریـــــــــک تمرین های شما به پایان رسید، برای ثبت، روی دکمه اتمام کلیک کنید");
        dialog.setTitleTextColor(R.color.aqua);
        dialog.setDescriptionTextColor(R.color.purple_700);
        dialog.setPositiveBtnText("اتمام");
        dialog.setPositiveBtnBackground(R.color.mainP);
        dialog.setNegativeBtnText("بازگشت به صفحه اصلی");
        dialog.setNegativeBtnBackground(R.color.myWhite);
        dialog.setGifResource(R.drawable.confrajulate);
        dialog.isCancellable(true);
        dialog.OnPositiveClicked(new FancyGifDialogListener() {
            @Override
            public void OnClick() {
                NavController controller = NavHostFragment.findNavController(practiceFragment);
                controller.navigate(R.id.mainFragment);

                if (status.equals("morning")) {
                    PracticeDatabase database = PracticeDatabase.getInstance(context);
                    database.idao().insert(morningPractice);
                } else {
                    PracticeDatabase database = PracticeDatabase.getInstance(context);
                    database.idaoNight().insert(nightPractice);
                }
                Toast.makeText(context, "تمرین های شما با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void configCircularCountDown() {
        //Set configuration for timer UI
        circularCountDownView.setTimerTextShown(true);
        circularCountDownView.setTimerType(HappyTimer.Type.COUNT_UP);
        circularCountDownView.setTimerTextFormat(CircularCountDownView.TextFormat.HOUR_MINUTE_SECOND);
        circularCountDownView.setStrokeThicknessForeground(10f);
        circularCountDownView.setStrokeThicknessBackground(10f);
        circularCountDownView.setStrokeColorBackground(ContextCompat.getColor(context, com.androchef.happytimer.R.color.colorLightBlue));
        circularCountDownView.setStrokeColorForeground(ContextCompat.getColor(context, R.color.mainP));
        circularCountDownView.setTimerTextColor(ContextCompat.getColor(context, R.color.mainP));
        circularCountDownView.setTimerTextIsBold(true);
        circularCountDownView.setTimerTextSize(13f);  //this will automatically converted to sp value.

        //Initialize Your Timer with seconds
        circularCountDownView.initTimer(68);

        circularCountDownView.setOnTickListener(new HappyTimer.OnTickListener() {
            @Override
            public void onTick(int i, int i1) {
            }

            @Override
            public void onTimeUp() {
                playHeroSound();
                dialog.build();

            }
        });
    }

    @Override
    public void playHeroSound() {
        final MediaPlayer player = MediaPlayer.create(context, R.raw.herosound);
        player.start();
    }

    @Override
    public void iniPracticeList() {
        if (status.equals("morning")) {
            practices = Shared.getQuestions(context, "morning");
        } else {
            practices = Shared.getQuestions(context, "night");
        }
    }

    @Override
    public void btnEdtPracticeClicked(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.editPracticeDialog(status, i, new IQuestionDialogCallBack() {
            @Override
            public void onSuccess() { // after dialog is dismissed
                iniPracticeList();
                txtPractice.setText(practices.get(i));
            }
        });
    }

    @Override
    public void btnResetClicked() {
        Shared.writeQuestions(context, "reset", 0, null); // to reset sharedpref
        iniPracticeList();
        txtPractice.setText(practices.get(i));
    }

    @Override
    public void btnGoToNextClicked(MaterialButton btnGoToNext, Activity requireActivity) {
        clickSound();
        if (status.equals("morning")) {
            fillingAnswersOperationMorning(btnGoToNext, requireActivity);

        } else { // for night
            fillingAnswersOperationNight();
        }
        edt_answer.setText("");
    }

    @Override
    public void fillingAnswersOperationMorning(MaterialButton btnGoToNext, Activity requireActivity) {
        if (i + 1 == Shared.morningQustions.size()) {
            Shared.writeIndexPractice("", context, status);
            i = 0;

            morningPractice.setMonth(date.getMonthOfYear());
            morningPractice.setDayOfMonth(date.getDayOfMonth());
            morningPractice.setYear(date.getYear());
            btnGoToNext.setClickable(false);
            circularCountDownView.setVisibility(View.VISIBLE);
            circularCountDownView.startTimer();
        } else {
            initMorningPractice(i, edt_answer.getText().toString());
            if (i == 5) {
                closeKeyboard(requireActivity);
                btnGoToNext.setText("شروع تمرین");
                edt_answer.setVisibility(View.GONE);
            }
            i++;
            Shared.writeIndexPractice(String.valueOf(i), context, status);
            txtPractice.setText(practices.get(i));
            cardView.setCardBackgroundColor(context.getResources().getColor(Shared.aquaPalate.get(i)));
            edt_answer.setTextColor(context.getResources().getColor(Shared.aquaPalateP.get(i)));
            runAnimation(txtPractice);

        }
    }

    @Override
    public void fillingAnswersOperationNight() {

        if (i + 1 == Shared.nightQusetions.size()) {
            Shared.writeIndexPractice("", context, status);
            i = 0;
            nightPractice.setMonth(date.getMonthOfYear());
            nightPractice.setDayOfMonth(date.getDayOfMonth());
            nightPractice.setYear(date.getYear());
            nightPractice.setThankGod(edt_answer.getText().toString()); // for last answer
            playHeroSound();
            dialog.build();

        } else {
            initNightPractice(i, edt_answer.getText().toString());

            i++;
            Shared.writeIndexPractice(String.valueOf(i), context, status);
            txtPractice.setText(practices.get(i));
            cardView.setCardBackgroundColor(context.getResources().getColor(Shared.aquaPalate.get(i)));
            edt_answer.setTextColor(context.getResources().getColor(Shared.aquaPalateP.get(i)));
            runAnimation(txtPractice);

        }
    }


    @Override
    public void closeKeyboard(Activity requireActivity) {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = requireActivity.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    context.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    @Override
    public void runAnimation(TextView tv) {
        Animation a = AnimationUtils.loadAnimation(context, R.anim.slide_left);
        a.reset();
        tv.clearAnimation();
        tv.startAnimation(a);
    }

    @Override
    public void initMorningPractice(int index, String answer) {

        switch (index) {
            case 0:
                morningPractice.setThanks_god(answer);
                break;
            case 1:
                morningPractice.setWho_thanks_god(answer);
                break;
            case 2:
                morningPractice.setMy_year_goal(answer);

                break;
            case 3:
                morningPractice.setWhat_do_today(answer);

                break;
            case 4:
                morningPractice.setThree_top(answer);

                break;
            case 5:
                morningPractice.setWhat_god_does(answer);

                break;

        }

    }

    @Override
    public void initNightPractice(int index, String answer) {
        switch (index) {
            case 0:
                nightPractice.setDid_i_done(answer);
                break;
            case 1:
                nightPractice.setWhat_for_tomorrow(answer);
                break;
            case 2:
                nightPractice.setOne_to_hundred(answer);

                break;
            case 3:
                nightPractice.setHow_tomorrow_better(answer);

                break;
            case 4:
                nightPractice.setWhat_nice_today(answer);

                break;
            case 5:
                nightPractice.setWhat_idea(answer);

                break;
            case 6:
                nightPractice.setThankGod(answer);

                break;

        }
    }

    @Override
    public void clickSound() {
        model.playClick();
    }
}
