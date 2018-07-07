package com.made.lianda.kamus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    SharedPreferences preferences;
    Context context;

    public AppPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun (Boolean input){
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.first_run);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.first_run);
        return preferences.getBoolean(key, true);
    }
}
