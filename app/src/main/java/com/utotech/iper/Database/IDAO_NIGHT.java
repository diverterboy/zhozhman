package com.utotech.iper.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utotech.iper.Model.MorningPractice;
import com.utotech.iper.Model.NightPractice;

import java.util.List;

@Dao
public interface IDAO_NIGHT {

    @Insert
    long insert(NightPractice nightPractice);

    @Delete
    void delete(NightPractice nightPractice);

    @Update
    void update(NightPractice nightPractice);

    @Query("select * from night_tbl_practice")
    List<NightPractice> getNightPracticeList();

    @Query("select EXISTS(SELECT * FROM night_tbl_practice WHERE id= :id)")
    boolean isExists(int id);

    @Query("DELETE FROM night_tbl_practice WHERE id= :id")
    int deleteVideoFav(int id);

    @Query("select * from night_tbl_practice WHERE dayOfMonth= :dayOfMonth AND month= :month AND year= :year")
    NightPractice getSpecificPractice(int dayOfMonth, int month, int year);
}
