package com.made.lianda.kamus.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_IN_EN = "table_in_en";
    static String TABLE_EN_IN = "tabl_en_in";

    public static final class IndonesiaColumns implements BaseColumns{
        public static String KATA = "kata";
        public static String TERJEMAH = "terjemah";
    }

    public static final class EnglishColumns implements BaseColumns{
        public static String WORD = "word";
        public static String TRANSLATE = "translate";
    }
}
