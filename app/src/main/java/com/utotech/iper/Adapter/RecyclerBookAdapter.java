package com.utotech.iper.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utotech.iper.R;
import com.utotech.iper.Utils.Shared;

public class RecyclerBookAdapter extends RecyclerView.Adapter<RecyclerBookAdapter.BookVh> {
    Context context;

    public RecyclerBookAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BookVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.books_row, null);
        return new BookVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookVh holder, int position) {
        int res = Shared.RESOURCES_LIST.get(position);
        holder.imageView.setImageResource(res);
    }

    @Override
    public int getItemCount() {
        return Shared.RESOURCES_LIST.size();
    }

    class BookVh extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BookVh(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgBook);
        }
    }
}
