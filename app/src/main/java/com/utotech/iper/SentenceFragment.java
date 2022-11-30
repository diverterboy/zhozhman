package com.utotech.iper;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.utotech.iper.Adapter.RecyclerSentenceListAdapter;
import com.utotech.iper.Database.DatabaseAccess;
import com.utotech.iper.Database.PracticeDatabase;
import com.utotech.iper.Model.HandlerModel;
import com.utotech.iper.Model.Sentence;
import com.utotech.iper.Utils.Dialog;
import com.utotech.iper.Utils.IDialogCallback;
import com.utotech.iper.Utils.Shared;
import com.utotech.iper.Utils.Tools;

import java.util.List;

public class SentenceFragment extends Fragment {
    FloatingActionButton fab_add, selectAll;
    BottomAppBar bottomAppBar;
    PracticeDatabase database;
    RecyclerView recycler_message;
    DatabaseAccess databaseAccess;
    HandlerModel model;
    RecyclerSentenceListAdapter adapter;
    TextView txtDate, numericdate;
    ImageView btnNavMenu, btn_setting;

    public SentenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sentence, container, false);
        initial(view);
        loadRecycler();

        Tools tools = new Tools();
        txtDate.setText(tools.getToday());
        numericdate.setText(tools.getNumericDate());

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.playClick(getContext());
                Dialog dialog = new Dialog(getActivity());
                dialog.addSentenceDialog(new IDialogCallback() {
                    @Override
                    public void onItemUpdated(List<Sentence> sentenceList) {
                        adapter.differ.submitList(sentenceList);
                    }
                });

            }
        });

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.playClick(getContext());
                databaseAccess.allBeShown();
                adapter.differ.submitList(databaseAccess.getSentences());
            }
        });

        btnNavMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shared.playClick(getContext());
                DrawerLayout drawerLayout = (DrawerLayout) MainActivity.toolbar.getParent().getParent();
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        MainActivity.toolbar.setVisibility(View.GONE);

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                controller.navigate(R.id.settingFragment);
            }
        });

        return view;
    }

    public void initial(View view) {
        bottomAppBar = view.findViewById(R.id.bottomAppBar);
        fab_add = view.findViewById(R.id.fab_add);
        numericdate = view.findViewById(R.id.numericdate);
        txtDate = view.findViewById(R.id.date);
        recycler_message = view.findViewById(R.id.recycler_message);
        selectAll = view.findViewById(R.id.selectAll);
        btnNavMenu = view.findViewById(R.id.btnNavMenu);
        btn_setting = view.findViewById(R.id.btn_setting);
        database = PracticeDatabase.getInstance(getContext());
        databaseAccess = DatabaseAccess.getInstance(getContext());
    }

    public void loadRecycler() {

        adapter = new RecyclerSentenceListAdapter(getActivity());
        adapter.differ.submitList(getSentencesList());
        recycler_message.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recycler_message.setAdapter(adapter);

/*
        List<Sentence> list = getSentencesList();
        adapter = new RecyclerSentenceListAdapter(getContext(), list, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycler_message.setAdapter(adapter);
        recycler_message.setLayoutManager(manager);*/

    }

    public List<Sentence> getSentencesList() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        List<Sentence> sentencesList = databaseAccess.getSentences();

        return sentencesList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.toolbar.setVisibility(View.VISIBLE);

    }

}