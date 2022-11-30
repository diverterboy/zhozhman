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

import com.squareup.picasso.Picasso;
import com.utotech.iper.R;
import com.utotech.iper.Utils.Shared;

import java.util.List;

public class RecyclerEssayAdapter extends RecyclerView.Adapter<RecyclerEssayAdapter.docVh> {
    Context context;
    List<String> titles, summaries, contents, images;
    NavController controlle;
    public RecyclerEssayAdapter(Context context ,  NavController controlle) {
        this.context = context;
        titles = Shared.titles;
        summaries = Shared.summaries;
        contents = Shared.content;
        images = Shared.essayImages;
        this.controlle = controlle;
    }

    @NonNull
    @Override
    public docVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.essay_row, null);
        return new docVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull docVh holder, int position) {
        if (position % 2 != 0) {
            holder.cardEssay.setCardBackgroundColor(context.getResources().getColor(R.color.aqua));
            holder.cardView2.setCardBackgroundColor(context.getResources().getColor(R.color.aqua));
            holder.cardView1.setCardBackgroundColor(context.getResources().getColor(R.color.aqua));

        }
        holder.txt_title.setText(titles.get(position));
        holder.txt_summary.setText(summaries.get(position));
        Picasso.get().load( images.get(position)).into(holder.imgEssayTitle);


        holder.cardEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.playClick(context);
                Bundle bundle = new Bundle();
                bundle.putInt("essayNumber", (holder.getAdapterPosition()));
                controlle.navigate(R.id.action_jazbFragment_to_essayShowFragment,bundle);
            }
        });

    }

    //todo probably were gonna remove this adapter
    @Override
    public int getItemCount() {
        return titles.size();
    }

    class docVh extends RecyclerView.ViewHolder {
        ImageView imgEssayTitle;
        TextView txt_title, txt_summary;
        CardView cardView2, cardView1, cardEssay;

        public docVh(@NonNull View itemView) {
            super(itemView);
            imgEssayTitle = itemView.findViewById(R.id.imgEssayTitle);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_summary = itemView.findViewById(R.id.txt_summary);
            cardView2 = itemView.findViewById(R.id.cardView2);
            cardView1 = itemView.findViewById(R.id.cardView1);
            cardEssay = itemView.findViewById(R.id.cardEssay);
        }
    }
}
