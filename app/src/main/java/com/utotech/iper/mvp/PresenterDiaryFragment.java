package com.utotech.iper.mvp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utotech.iper.Adapter.RecyclerDaysAdapter;

public class PresenterDiaryFragment implements ContractDiaryFragment.Presenter {
    Context context;
    public PresenterDiaryFragment(Context context){
        this.context = context;
    }

    @Override
    public String checkMonth(int monthNumber) {
        String result = "";
        switch (monthNumber) {
            case 0:
                result = "فروردین";
                break;

            case 1:
                result = "اردیبهشت";
                break;
            case 2:
                result = "خرداد";
                break;
            case 3:
                result = "تیر";
                break;
            case 4:
                result = "مرداد";
                break;
            case 5:
                result = "شهریور";
                break;
            case 6:
                result = "مهر";
                break;
            case 7:
                result = "آبان";
                break;
            case 8:
                result = "آذر";
                break;
            case 9:
                result = "دی";
                break;
            case 10:
                result = "بهمن";
                break;
            case 11:
                result = "اسفند";

        }
        return result;
    }

    @Override
    public void runRecyclerDays(int monthNumber , Fragment fragment, RecyclerView recyclerDays) {
        NavController controller = NavHostFragment.findNavController(fragment);
        recyclerDays.setAdapter(new RecyclerDaysAdapter(context, monthNumber, controller, monthNumber));
        recyclerDays.setLayoutManager(new GridLayoutManager(context, 4, RecyclerView.VERTICAL, false));
    }


}
