package com.utotech.iper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utotech.iper.Adapter.RecyclerMonthsAdapter;

public class MonthFragment extends Fragment {

    RecyclerView recyclerView;
    TextView textView;

    public MonthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initial
        View view = inflater.inflate(R.layout.fragment_months, container, false);
        recyclerView = view.findViewById(R.id.recycler_months);
        NavController controller = NavHostFragment.findNavController(this);
        RecyclerMonthsAdapter adapter = new RecyclerMonthsAdapter(getContext(), controller);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);

        // runRecycler
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        //initTextview
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("مشاهده گزارش");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }
}