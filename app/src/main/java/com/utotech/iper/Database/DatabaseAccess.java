package com.utotech.iper.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Shader;
import android.util.Log;

import com.utotech.iper.Model.Sentence;
import com.utotech.iper.Utils.Shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseAccess {
    private final SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    public static final String TABLE_Sentence = "sentence";
    public static final String KEY_ID = "id";
    public static final String KEY_beShown = "be_shown";
    public static final String KEY_REPEAT_NUM = "repeat_num";
    QsentenceDbAdapter adapter;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
        adapter = new QsentenceDbAdapter(context);

    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the databases connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the databases connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the databases.
     *
     * @return a List of quotes
     */

    public long addSentence(Sentence sentence) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_Sentence, sentence.getSentence());
        contentValues.put(KEY_REPEAT_NUM, sentence.getRepeatNum());
        contentValues.put(KEY_beShown, 1); // to be shown
        long result = database.insert(TABLE_Sentence, null, contentValues);
        // in order to add sentence to static list
        if (sentence.getBeShowen() == 1) {
            for (int i = 0; i <= sentence.getRepeatNum(); i++) {
                //Shared.qSentences.add(sentence.getSentence());
                adapter.addNewQs(sentence.getSentence());
            }
        }
        close();
        return result;
    }

    public List<Sentence> getSentences() {
        open();
        List<Sentence> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_Sentence, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sentence sentence = new Sentence();

            sentence.setId(cursor.getInt(0));
            sentence.setSentence(cursor.getString(1));
            sentence.setBeShowen(cursor.getInt(2));
            sentence.setRepeatNum(cursor.getInt(3));
            list.add(sentence);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }

    public long deleteSentence(int id) {
        open();
        long result = database.delete(TABLE_Sentence, KEY_ID + "=" + id, null);
        close();

        return result;
    }

    public void deleteAll() {
        open();
        database.execSQL("delete from " + TABLE_Sentence);
        close();
    }

    public Boolean updateSentence(String id, int beShown, int repeatNum, String sentence, int prevBeShown, boolean fromDialog) {
        open();


        ContentValues contentValues = new ContentValues();
        if (fromDialog) {
            if (repeatNum == 5200) {
                contentValues.put(TABLE_Sentence, sentence);

            } else {
                contentValues.put(KEY_REPEAT_NUM, repeatNum);
                contentValues.put(TABLE_Sentence, sentence);
            }
        } else {
            contentValues.put(KEY_beShown, beShown);
        }

        database.update(TABLE_Sentence, contentValues, KEY_ID + "=" + id, null);


        if (prevBeShown == beShown || !fromDialog) {
        } else {
            if (prevBeShown == 0) {
                for (int i = 0; i <= repeatNum; i++) {
                    adapter.addNewQs(sentence);
                    //  Shared.qSentences.add(sentence);
                }
            } else if (prevBeShown == 1) {
                indexOf(sentence);
            }
        }
        close();
        return true;
    }

    @SuppressLint("Range")
    public List<Sentence> getShownSentences() {
        open();
        List<Sentence> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_Sentence, null);
        cursor.moveToFirst();
        int count = 0;
        while (cursor.moveToNext()) {
            if (cursor.getInt(2) == 1) {
                int i = cursor.getInt(3);
                count = count + i;
                Sentence sentence = new Sentence();
                sentence.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                sentence.setRepeatNum(cursor.getInt(cursor.getColumnIndex(KEY_REPEAT_NUM)));
                sentence.setBeShowen(cursor.getInt(cursor.getColumnIndex(KEY_beShown)));
                sentence.setSentence(cursor.getString(cursor.getColumnIndex(TABLE_Sentence)));

                list.add(sentence);

            }
        }
        cursor.close();
        close();

        return list;
    }

    public void indexOf(String sentence) {
       /* while (true) {
            if (Shared.qSentences.contains(sentence)) {
                Shared.qSentences.remove(sentence);
            } else {
                break;
            }

        }*/
        adapter.indexOf(sentence);
    }

    @SuppressLint("Range")
    public Sentence getSentence(int id) {
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_Sentence, null);
        cursor.moveToFirst();
        Sentence sentence = new Sentence();
        while (cursor.moveToNext()) {
            if (cursor.getInt(0) == id) {
                sentence.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                sentence.setRepeatNum(cursor.getInt(cursor.getColumnIndex(KEY_REPEAT_NUM)));
                sentence.setBeShowen(cursor.getInt(cursor.getColumnIndex(KEY_beShown)));
                sentence.setSentence(cursor.getString(cursor.getColumnIndex(TABLE_Sentence)));
                break;
            }
        }
        close();
        cursor.close();
        return sentence;
    }

    public void allBeShown() {
        open();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_Sentence, null);

        int active = 0;
        int deactive = 0;

        while (cursor.moveToNext()) {
            int beShown = cursor.getInt(cursor.getColumnIndex(KEY_beShown));
            if (beShown == 1) {
                active++;
            } else {
                deactive++;
            }
        }

        cursor.moveToFirst();
        cursor.moveToPrevious();


        if (active > deactive) {

            contentValues.put(KEY_beShown, 0);
            while (cursor.moveToNext()) {

                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                database.update(TABLE_Sentence, contentValues, KEY_ID + "=" + id, null);

            }
        } else {

            contentValues.put(KEY_beShown, 1);
            while (cursor.moveToNext()) {
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                database.update(TABLE_Sentence, contentValues, KEY_ID + "=" + id, null);
            }
        }

        close();


    }

    public void addQsentences() {
        open();
        ContentValues contentValues = new ContentValues();


    }
}
