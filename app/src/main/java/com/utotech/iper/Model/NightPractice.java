package com.utotech.iper.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "night_tbl_practice")
public class NightPractice {
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


    @SerializedName("did_i_done")
    @Expose
    @ColumnInfo(name = "did_i_done")
    private String did_i_done;

    @SerializedName("what_for_tomorrow")
    @Expose
    @ColumnInfo(name = "what_for_tomorrow")
    private String what_for_tomorrow;

    @SerializedName("one_to_hundred")
    @Expose
    @ColumnInfo(name = "one_to_hundred")
    private String one_to_hundred;

    @SerializedName("how_tomorrow_better")
    @Expose
    @ColumnInfo(name = "how_tomorrow_better")
    private String how_tomorrow_better;

    @SerializedName("what_nice_today")
    @Expose
    @ColumnInfo(name = "what_nice_today")
    private String what_nice_today;

    @SerializedName("what_idea")
    @Expose
    @ColumnInfo(name = "what_idea")
    private String what_idea;

    @SerializedName("thankGod")
    @Expose
    @ColumnInfo(name = "thankGod")
    private String thankGod;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDid_i_done() {
        return did_i_done;
    }

    public void setDid_i_done(String did_i_done) {
        this.did_i_done = did_i_done;
    }

    public String getWhat_for_tomorrow() {
        return what_for_tomorrow;
    }

    public void setWhat_for_tomorrow(String what_for_tomorrow) {
        this.what_for_tomorrow = what_for_tomorrow;
    }

    public String getOne_to_hundred() {
        return one_to_hundred;
    }

    public void setOne_to_hundred(String one_to_hundred) {
        this.one_to_hundred = one_to_hundred;
    }

    public String getHow_tomorrow_better() {
        return how_tomorrow_better;
    }

    public void setHow_tomorrow_better(String how_tomorrow_better) {
        this.how_tomorrow_better = how_tomorrow_better;
    }

    public String getWhat_nice_today() {
        return what_nice_today;
    }

    public void setWhat_nice_today(String what_nice_today) {
        this.what_nice_today = what_nice_today;
    }

    public String getWhat_idea() {
        return what_idea;
    }

    public void setWhat_idea(String what_idea) {
        this.what_idea = what_idea;
    }

    public String getThankGod() {
        return thankGod;
    }

    public void setThankGod(String thankGod) {
        this.thankGod = thankGod;
    }

}
