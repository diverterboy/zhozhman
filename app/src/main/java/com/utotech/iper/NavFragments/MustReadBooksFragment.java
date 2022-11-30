package com.utotech.iper.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utotech.iper.Adapter.RecyclerBookAdapter;
import com.utotech.iper.MainActivity;
import com.utotech.iper.R;


public class MustReadBooksFragment extends Fragment {
    RecyclerView recyclerBooks;
    TextView textView;

    public MustReadBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("کتاب هایی که باید بخوانید");

        View view = inflater.inflate(R.layout.fragment_must_read_books, container, false);
        recyclerBooks = view.findViewById(R.id.recyclerBooks);
        recyclerBooks.setAdapter(new RecyclerBookAdapter(getContext()));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }
}