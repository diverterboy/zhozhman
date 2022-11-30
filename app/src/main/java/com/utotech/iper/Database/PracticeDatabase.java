package com.utotech.iper.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;

@Database(entities = {MorningPractice.class, NightPractice.class}, version = 1, exportSchema = true)
public abstract class PracticeDatabase extends RoomDatabase {

    private static PracticeDatabase instance = null;
    public abstract IDAO_MORNING idao();
    public abstract IDAO_NIGHT idaoNight();

    public static synchronized PracticeDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, PracticeDatabase.class, "practice.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
