package com.utotech.iper.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.utotech.iper.R;
import com.utotech.iper.Utils.Shared;

import java.util.Arrays;
import java.util.List;

public class RecyclerMonthsAdapter extends RecyclerView.Adapter<RecyclerMonthsAdapter.MonthsVh> {
    Context context;
    NavController controller;

    public RecyclerMonthsAdapter(Context context, NavController controller) {
        this.context = context;
        this.controller = controller;
    }

    @NonNull
    @Override
    public MonthsVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.month_row, null);
        return new MonthsVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthsVh holder, int position) {
        holder.txt_month.setText(monthList.get(position));
        holder.imgMonth.setImageResource(monthImgList.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.playClick(context);
                Bundle bundle = new Bundle();
                bundle.putInt("monthNumber", holder.getAdapterPosition());
                controller.navigate(R.id.action_monthFragment_to_diaryFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return monthList.size();
    }

    class MonthsVh extends RecyclerView.ViewHolder {
        TextView txt_month;
        ImageView imgMonth;
        CardView cardView;
        public MonthsVh(@NonNull View itemView) {
            super(itemView);
            txt_month = itemView.findViewById(R.id.txt_month);
            imgMonth = itemView.findViewById(R.id.imgMonth);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

    private final List<String> monthList = Arrays.asList("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند");
    private final List<Integer> monthImgList = Arrays.asList(R.drawable.farvardin, R.drawable.ordibehesht, R.drawable.khordad, R.drawable.tir, R.drawable.mordad, R.drawable.shahrivar, R.drawable.mehr, R.drawable.aban, R.drawable.azar, R.drawable.dey, R.drawable.bahman, R.drawable.esfand);
}
