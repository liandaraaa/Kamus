package com.made.lianda.kamus.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.made.lianda.kamus.model.English;
import com.made.lianda.kamus.model.Indonesia;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.made.lianda.kamus.db.DatabaseContract.EnglishColumns.TRANSLATE;
import static com.made.lianda.kamus.db.DatabaseContract.EnglishColumns.WORD;
import static com.made.lianda.kamus.db.DatabaseContract.IndonesiaColumns.KATA;
import static com.made.lianda.kamus.db.DatabaseContract.IndonesiaColumns.TERJEMAH;
import static com.made.lianda.kamus.db.DatabaseContract.TABLE_EN_IN;
import static com.made.lianda.kamus.db.DatabaseContract.TABLE_IN_EN;

public class KamusHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }


    public ArrayList<Indonesia> getAllIndonesiaData(){

        Cursor cursor = database.query(TABLE_IN_EN,null,null,null,null,null,_ID+ " ASC",null);
        cursor.moveToFirst();

        ArrayList<Indonesia> arrayList = new ArrayList<>();
        Indonesia kamusIndonesia;

        if (cursor.getCount() > 0){
            do {
                kamusIndonesia = new Indonesia();
                kamusIndonesia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusIndonesia.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusIndonesia.setTerjemah(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAH)));

                arrayList.add(kamusIndonesia);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public ArrayList<English> getAllEnglishData(){

        Cursor cursor = database.query(TABLE_EN_IN,null,null,null,null,null,_ID+ " ASC",null);
        cursor.moveToFirst();

        ArrayList<English> arrayList = new ArrayList<>();
        English english;

        if (cursor.getCount() > 0){
            do {
                english = new English();
                english.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                english.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                english.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE)));

                arrayList.add(english);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return arrayList;
    }

    public Cursor searchResultIndonesia(String search){
        String[] columns = {_ID, KATA, TERJEMAH};
        Cursor cursor = null;

        if (search != null && search.length()>0){
            String sql = "SELECT * FROM "+TABLE_IN_EN+" WHERE "+KATA+" LIKE '%"+search+"%'";
            cursor = database.rawQuery(sql, null);
            return cursor;
        }

        cursor = database.query(TABLE_IN_EN, columns, null, null, null,
                null,null);
        return cursor;
    }

    public Cursor searchResultEnglish(String search){
        String[] columns = {_ID, WORD, TRANSLATE};
        Cursor cursor = null;

        if (search != null && search.length()>0){
            String sql = "SELECT * FROM "+TABLE_EN_IN+" WHERE "+WORD+" LIKE '%"+search+"%'";
            cursor = database.rawQuery(sql, null);
            return cursor;
        }

        cursor = database.query(TABLE_EN_IN, columns, null, null, null,
                null,null);
        return cursor;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransactionIndonesia(Indonesia kamusIndonesia){
        String sql = "INSERT INTO "+TABLE_IN_EN+" ("+KATA+", "+TERJEMAH+") VALUES (?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, kamusIndonesia.getKata());
        statement.bindString(2, kamusIndonesia.getTerjemah());
        statement.execute();
        statement.clearBindings();

    }

    public void insertTransactionEnglish(English english){
        String sql = "INSERT INTO "+TABLE_EN_IN+" ("+WORD+", "+TRANSLATE+") VALUES (?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, english.getWord());
        statement.bindString(2, english.getTranslate());
        statement.execute();
        statement.clearBindings();
    }
}
