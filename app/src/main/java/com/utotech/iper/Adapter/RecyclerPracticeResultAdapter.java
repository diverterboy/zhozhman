package com.utotech.iper.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.utotech.iper.R;
import com.utotech.iper.Utils.Shared;


import java.util.ArrayList;
import java.util.List;

public class RecyclerPracticeResultAdapter extends RecyclerView.Adapter<RecyclerPracticeResultAdapter.ResultVh> {
    Context context;
    int month;
    List<String> questionList;
    List<String> answersList;

    public RecyclerPracticeResultAdapter(Context context, int month, List<String> answersList) {
        this.context = context;
        this.month = month;
        this.answersList = answersList;
        questionList = new ArrayList<>();
        questionList.addAll(Shared.morningQustions);
        questionList.addAll(Shared.nightQusetions);
        questionList.remove(6); // to remove focus item

    }

    @NonNull
    @Override
    public ResultVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_below_api23, null);
        return new ResultVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultVh holder, int position) {
        String question = questionList.get(position);
        String answers = answersList.get(position);
        holder.txt_message.setText(question);
        holder.txt_count.setText(answers);


    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ResultVh extends RecyclerView.ViewHolder {
        TextView txt_message, txt_count;
        CardView cardview;


        public ResultVh(@NonNull View itemView) {
            super(itemView);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_message = itemView.findViewById(R.id.txt_message);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
}
