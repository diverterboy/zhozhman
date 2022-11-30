package com.utotech.iper.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QSentencesDatabase extends SQLiteOpenHelper {

    public static final String DB_Qsentence_NAME = "QSENTENCE.db";
    private static final int QSENTENCE_VERSION = 1;
    String TABLE_QSENTENCES = "tbl_qsentences";

    public QSentencesDatabase(@Nullable Context context) {
        super(context, DB_Qsentence_NAME, null, QSENTENCE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + TABLE_QSENTENCES + "(id Integer ,qsentence varchar (1000) )";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
