package com.utotech.iper.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.AsyncListDiffer;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.utotech.iper.Database.DatabaseAccess;
import com.utotech.iper.Model.Sentence;
import com.utotech.iper.R;

public class Dialog {
    Activity context;
    DatabaseAccess databaseAccess;

    public Dialog(Activity context) {
        this.context = context;
        databaseAccess = DatabaseAccess.getInstance(context);

    }

    public void addSentenceDialog(IDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("اضافه کردن جمله");
        final View customLayout = context.getLayoutInflater().inflate(R.layout.add_dialog, null);
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        dialog.show();

        //initial views
        TextInputEditText edt_input_txt = customLayout.findViewById(R.id.edt_input_txt);
        TextInputEditText txt_repeat = customLayout.findViewById(R.id.txt_repeat);
        Button btn_add = customLayout.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sentence sentence = new Sentence();
                sentence.setSentence(edt_input_txt.getText().toString());
                if (txt_repeat.getText().toString().equals("")) {
                    sentence.setRepeatNum(1);
                } else {
                    sentence.setRepeatNum(Integer.parseInt(txt_repeat.getText().toString()));

                }
                databaseAccess.addSentence(sentence);
                Toast.makeText(context, "جمله با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                callback.onItemUpdated(databaseAccess.getSentences());
                dialog.dismiss();
            }
        });


    }

    public void editSentenceDialog(int id, String sentence, AsyncListDiffer differ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ویرایش جمله و تعداد آن");
        final View customLayout = context.getLayoutInflater().inflate(R.layout.edit_dialog, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.show();

        //initial views
        TextInputEditText edt_input_txt = customLayout.findViewById(R.id.edt_input_txt);
        TextInputEditText txt_repeat = customLayout.findViewById(R.id.txt_repeat);
        Button btn_edit = customLayout.findViewById(R.id.btn_add);
        Button btn_remove = customLayout.findViewById(R.id.btn_remove);

        edt_input_txt.setText(sentence);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int repeat;
                if (txt_repeat.getText().toString().equals("")) {
                    repeat = 5200;
                    // to avoid updating in DB
                } else {
                    repeat = Integer.parseInt(txt_repeat.getText().toString());
                }
                databaseAccess.updateSentence(String.valueOf(id), 99, repeat, // we set beshown to 99 because we dont need any change from here
                        edt_input_txt.getText().toString(), 99, true);
                Toast.makeText(context, "جمله با موفقیت آپدیت شد", Toast.LENGTH_SHORT).show();
                differ.submitList(databaseAccess.getSentences());
                dialog.dismiss();
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseAccess.deleteSentence(id);
                differ.submitList(databaseAccess.getSentences());
                dialog.dismiss();
            }
        });


    }

    public void editPracticeDialog(String whichQuestion, int questionIndex, IQuestionDialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("تغییر تمرین");
        final View customLayout = context.getLayoutInflater().inflate(R.layout.edt_practice_dialog, null);
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        dialog.show();

        //initial views
        TextInputEditText edt_input_txt = customLayout.findViewById(R.id.edt_input_txt);
        Button btn_edit = customLayout.findViewById(R.id.btn_edit);
        Button btnCancel = customLayout.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newSentence = edt_input_txt.getText().toString();
                Shared.writeQuestions(context, whichQuestion, questionIndex, newSentence);
                callBack.onSuccess();
                dialog.dismiss();
            }
        });
    }

    public void fontDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("فونت مد نظر خود را انتخاب کنید");
        final View customLayout = context.getLayoutInflater().inflate(R.layout.dialog_font, null);
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        dialog.show();

        //initial views
        Button btnSols, btnGandom, btnIranNastaligh, btnNazanin, btnDefault;
        btnDefault = customLayout.findViewById(R.id.btnDefault);
        btnNazanin = customLayout.findViewById(R.id.btnNazanin);
        btnIranNastaligh = customLayout.findViewById(R.id.btnIranNastaligh);
        btnGandom = customLayout.findViewById(R.id.btnGandom);
        btnSols = customLayout.findViewById(R.id.btnSols);

        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shared.writeAppSetting(context, "fontToastyName", "default");
                Shared.writeAppSetting(context, "fontName", "default");
                dialog.dismiss();
                Snackbar.make(view, "فونت مورد نظر شما انتخاب شد", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnNazanin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shared.writeAppSetting(context, "fontToastyName", "nazanin.ttf");
                Shared.writeAppSetting(context, "fontName", "nazanin.ttf");
                dialog.dismiss();
                Snackbar.make(view, "فونت مورد نظر شما انتخاب شد", Snackbar.LENGTH_SHORT).show();
            }
        });


        btnIranNastaligh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shared.writeAppSetting(context, "fontToastyName", "irannastaliq.ttf");


                Shared.writeAppSetting(context, "fontName", "irannastaliq.ttf");


                dialog.dismiss();
                Snackbar.make(view, "فونت مورد نظر شما انتخاب شد", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnGandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shared.writeAppSetting(context, "fontToastyName", "gandom.ttf");


                Shared.writeAppSetting(context, "fontName", "gandom.ttf");


                dialog.dismiss();
                Snackbar.make(view, "فونت مورد نظر شما انتخاب شد", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnSols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shared.writeAppSetting(context, "fontToastyName", "suls.ttf");


                Shared.writeAppSetting(context, "fontName", "suls.ttf");


                dialog.dismiss();
                Snackbar.make(view, "فونت مورد نظر شما انتخاب شد", Snackbar.LENGTH_SHORT).show();
            }
        });


    }

    public void whenToDialog(IQuestionDialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("انتخاب فاصله زمانی");

        final View customLayout = context.getLayoutInflater().inflate(R.layout.dialog_when_to, null);
        builder.setView(customLayout);

        AlertDialog dialog = builder.create();
        dialog.show();


        //initialViews
        Button btn5Min = customLayout.findViewById(R.id.btn5Min);
        Button btn10Min = customLayout.findViewById(R.id.btn10Min);
        Button btn20Min = customLayout.findViewById(R.id.btn20Min);
        Button btn30Min = customLayout.findViewById(R.id.btn30Min);
        Button btnOneHour = customLayout.findViewById(R.id.btnOneHour);
        Button btn45sec = customLayout.findViewById(R.id.btn45sec);

        // to make change for the one that is choosen
        String value = Shared.readAppSetting(context, "whenTo");
        switch (value) {
            case "1":
                btnOneHour.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
                btnOneHour.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "5":
                btn5Min.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
                btn5Min.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "10":
                btn10Min.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
                btn10Min.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "20":
                btn20Min.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
                btn20Min.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "30":
                btn30Min.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
                btn30Min.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case "45sec":
                btn45sec.setBackgroundColor(context.getResources().getColor(R.color.purple_500));
                btn45sec.setTextColor(context.getResources().getColor(R.color.white));
                break;

        }
        // to make change for the one that is choosen

        btn5Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.writeAppSetting(context, "whenTo", "5");
                callBack.onSuccess();
                dialog.dismiss();
            }
        });

        btn10Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.writeAppSetting(context, "whenTo", "10");
                callBack.onSuccess();
                dialog.dismiss();
            }
        });

        btn20Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.writeAppSetting(context, "whenTo", "20");
                callBack.onSuccess();
                dialog.dismiss();
            }
        });


        btn30Min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.writeAppSetting(context, "whenTo", "30");
                callBack.onSuccess();
                dialog.dismiss();
            }
        });


        btnOneHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.writeAppSetting(context, "whenTo", "1");
                callBack.onSuccess();
                dialog.dismiss();
            }
        });

        btn45sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.writeAppSetting(context, "whenTo", "45sec");
                callBack.onSuccess();
                dialog.dismiss();
            }
        });


    }

}
