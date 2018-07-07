package com.made.lianda.kamus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.made.lianda.kamus.db.DatabaseContract.EnglishColumns.TRANSLATE;
import static com.made.lianda.kamus.db.DatabaseContract.EnglishColumns.WORD;
import static com.made.lianda.kamus.db.DatabaseContract.IndonesiaColumns.KATA;
import static com.made.lianda.kamus.db.DatabaseContract.IndonesiaColumns.TERJEMAH;
import static com.made.lianda.kamus.db.DatabaseContract.TABLE_EN_IN;
import static com.made.lianda.kamus.db.DatabaseContract.TABLE_IN_EN;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_IN_EN= "create table "+TABLE_IN_EN+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA+" text not null, " +
            TERJEMAH+" text not null);";

    public static String CREATE_TABLE_EN_IN = "create table "+TABLE_EN_IN+
            " ("+_ID+" integer primary key autoincrement, "+
            WORD+" text not null, "+TRANSLATE+" text not null);";

    public DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_IN_EN);
        sqLiteDatabase.execSQL(CREATE_TABLE_EN_IN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_IN_EN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_EN_IN);
        onCreate(sqLiteDatabase);
    }
}
