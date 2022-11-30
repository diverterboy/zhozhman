package com.utotech.iper.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "morning_tbl_practice")
public class MorningPractice {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("dayOfMonth")
    @Expose
    @ColumnInfo(name = "dayOfMonth")
    private int dayOfMonth;

    @SerializedName("month")
    @Expose
    @ColumnInfo(name = "month")
    private int month;

    @SerializedName("year")
    @Expose
    @ColumnInfo(name = "year")
    private int year;


    @SerializedName("thanks_god")
    @Expose
    @ColumnInfo(name = "thanks_god")
    private String thanks_god;

    @SerializedName("who_thanks_god")
    @Expose
    @ColumnInfo(name = "who_thanks_god")
    private String who_thanks_god;

    @SerializedName("my_year_goal")
    @Expose
    @ColumnInfo(name = "my_year_goal")
    private String my_year_goal;

    @SerializedName("what_do_today")
    @Expose
    @ColumnInfo(name = "what_do_today")
    private String what_do_today;

    @SerializedName("three_top")
    @Expose
    @ColumnInfo(name = "three_top")
    private String three_top;

    @SerializedName("what_god_does")
    @Expose
    @ColumnInfo(name = "what_god_does")
    private String what_god_does;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getThanks_god() {
        return thanks_god;
    }

    public void setThanks_god(String thanks_god) {
        this.thanks_god = thanks_god;
    }

    public String getWho_thanks_god() {
        return who_thanks_god;
    }

    public void setWho_thanks_god(String who_thanks_god) {
        this.who_thanks_god = who_thanks_god;
    }

    public String getMy_year_goal() {
        return my_year_goal;
    }

    public void setMy_year_goal(String my_year_goal) {
        this.my_year_goal = my_year_goal;
    }

    public String getWhat_do_today() {
        return what_do_today;
    }

    public void setWhat_do_today(String what_do_today) {
        this.what_do_today = what_do_today;
    }

    public String getThree_top() {
        return three_top;
    }

    public void setThree_top(String three_top) {
        this.three_top = three_top;
    }

    public String getWhat_god_does() {
        return what_god_does;
    }

    public void setWhat_god_does(String what_god_does) {
        this.what_god_does = what_god_does;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
