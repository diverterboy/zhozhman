package com.utotech.iper.Adapter;

import android.content.Context;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.utotech.iper.Database.PracticeDatabase;

import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;
import com.utotech.iper.R;
import com.utotech.iper.Utils.Shared;
import com.utotech.iper.Utils.Tools;

public class RecyclerDaysAdapter extends RecyclerView.Adapter<RecyclerDaysAdapter.DaysVh> {
    Context context;
    int month;
    NavController controller;
    int monthNumber;

    public RecyclerDaysAdapter(Context context, int month, NavController controller, int monthNumber) {
        this.context = context;
        this.month = month + 1;
        this.controller = controller;
        this.monthNumber = monthNumber;
    }

    @NonNull
    @Override
    public DaysVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.days_row, null);
        return new DaysVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysVh holder, int position) {
        holder.txt_month.setGravity(Gravity.CENTER_HORIZONTAL);
        String s = String.valueOf(position + 1);
        holder.txt_month.setText(s);

        holder.txt_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.playClick(context);
                Tools tools = new Tools();
                PracticeDatabase practiceDatabase = PracticeDatabase.getInstance(context);
                NightPractice nightPractice = practiceDatabase.idaoNight().getSpecificPractice(holder.getAdapterPosition() + 1, monthNumber + 1, 1401);
                MorningPractice morningPractice = practiceDatabase.idao().getSpecificPractice(holder.getAdapterPosition() + 1, monthNumber + 1, 1401);

                if (nightPractice== null || morningPractice==null) {
                    Snackbar.make(view, "تمرین های این روز کامل نشده!", Snackbar.LENGTH_SHORT).show();
                } else {

                    Bundle bundle = new Bundle();
                    bundle.putInt("monthNumber", monthNumber + 1);
                    bundle.putInt("dayNumber", holder.getAdapterPosition() + 1);
                    controller.navigate(R.id.action_diaryFragment_to_reportFragment, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (month <= 6) {
            return 31;
        } else if (month == 12) {
            return 29;
        } else {
            return 30;
        }
    }

    class DaysVh extends RecyclerView.ViewHolder {
        TextView txt_month;
        CardView cardView;

        public DaysVh(@NonNull View itemView) {
            super(itemView);
            txt_month = itemView.findViewById(R.id.txt_month);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
