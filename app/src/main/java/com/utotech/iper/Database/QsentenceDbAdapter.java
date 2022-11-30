package com.utotech.iper.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.utotech.iper.Utils.Shared;

import java.util.ArrayList;
import java.util.List;

public class QsentenceDbAdapter extends QSentencesDatabase {
    public static final String KEY_SENTENCE = "qsentence";
    public static final String KEY_ID = "id";
    Context context;

    public QsentenceDbAdapter(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public void addNewQs(String qSentence) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SENTENCE, qSentence);
        db.insert(TABLE_QSENTENCES, null, contentValues);
    }

    public String getSentence(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_QSENTENCES, null);
        String result = null;
        while (cursor.moveToNext()) {
            if (cursor.getInt(cursor.getInt(1)) == id) {
                result = cursor.getString(cursor.getColumnIndex(KEY_SENTENCE));
                break;
            }
        }

        cursor.close();
        return result;
    }

    public void onGetQsentenced(List<String> stringList) {
        // we fill table with sentnece again
        SQLiteDatabase db = getWritableDatabase();
        Log.e("", "");
        for (int i = 0; i < stringList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_SENTENCE, stringList.get(i));
            contentValues.put(KEY_ID, i);
            db.insert(TABLE_QSENTENCES, null, contentValues);
        }
        Shared.writeAppSetting(context, "qSentenceIndex", "0");
    }

    public void deleteLastItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_QSENTENCES, KEY_ID + "=" + id, null);
        db.close();

    }

    public boolean isEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_QSENTENCES, null);
        boolean rowExists;

        if (mCursor.moveToFirst()) {
            // DO SOMETHING WITH CURSOR
            rowExists = false;

        } else {
            // I AM EMPTY
            rowExists = true;
        }
        db.close();
        mCursor.close();
        return rowExists;
    }

    public void indexOf(String sentence) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_QSENTENCES, null);
        while (cursor.moveToNext()) {
            if (cursor.getString(0).contains(sentence)) {
                int id = cursor.getInt(1);
                deleteLastItem(id);
                break;
            }
        }

        cursor.close();
    }

    public List<String> getAllQ() {
        SQLiteDatabase db = getReadableDatabase();
        List<String> recordList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + TABLE_QSENTENCES, null);
        while (cursor.moveToNext()) {
            recordList.add(cursor.getString(0));
        }
        // close for preventing from lack of memory
        cursor.close();
        db.close();
        return recordList;
    }
}
