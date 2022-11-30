package com.utotech.iper.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utotech.iper.Model.MorningPractice;

import java.util.List;

@Dao
public interface IDAO_MORNING {

    @Insert
    long insert(MorningPractice morningPractice);

    @Delete
    void delete(MorningPractice morningPractice);

    @Update
    void update(MorningPractice morningPractice);

    @Query("select * from morning_tbl_practice")
    List<MorningPractice> getMorningPracticeList();

    @Query("select EXISTS(SELECT * FROM morning_tbl_practice WHERE id= :id)")
    boolean isExists(int id);

    @Query("DELETE FROM morning_tbl_practice WHERE id= :id")
    int deleteVideoFav(int id);

    @Query("select * from morning_tbl_practice WHERE dayOfMonth= :dayOfMonth AND month= :month AND year= :year")
    MorningPractice getSpecificPractice(int dayOfMonth, int month, int year);
}
