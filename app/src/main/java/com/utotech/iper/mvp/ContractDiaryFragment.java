package com.utotech.iper.mvp;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public interface ContractDiaryFragment {
    interface View{
        void initial(android.view.View view);
       String checkMonth(int monthNumber);
       void setUp();
    }

    interface Presenter{
        String checkMonth(int monthNumber);

        void runRecyclerDays(int monthNumber , Fragment fragment, RecyclerView recyclerDays);

    }
}
