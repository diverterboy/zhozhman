package com.utotech.iper.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utotech.iper.Adapter.RecyclerEssayAdapter;
import com.utotech.iper.MainActivity;
import com.utotech.iper.R;


public class EssayListFragment extends Fragment {
    RecyclerView recycler_essay;
    TextView textView;

    public EssayListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_essay_list, container, false);
        recycler_essay = view.findViewById(R.id.recycler_essay);

        NavController controller = NavHostFragment.findNavController(this);
        textView = (TextView) MainActivity.toolbar.getChildAt(3); //to get text view
        textView.setText("مقاله ها");

        recycler_essay.setAdapter(new RecyclerEssayAdapter(getContext(),controller));
        recycler_essay.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textView.setText("");
    }
}