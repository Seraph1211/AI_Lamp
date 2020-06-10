package com.coolweather.ai_lamp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class MySharedPreferencesUtils {

    public static void putInt(Context context, String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();

        Log.d(TAG, "putInt: ["+key+", "+value+"]");
    }

    public static int getInt(Context context, String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.d(TAG, "getInt: ["+key+", "+sharedPreferences.getInt(key, -2)+"]");

        return sharedPreferences.getInt(key, -2);  //默认值设为-2

    }

    public static void putString(Context context, String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

        Log.d(TAG, "putString: ["+key+", "+value+"]");
    }

    public static String getString(Context context, String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.d(TAG, "getString: ["+key+", "+sharedPreferences.getString(key, "empty")+"]");

        return sharedPreferences.getString(key, "empty");  //默认值设为"empty"
    }

    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();

        Log.d(TAG, "putBoolean: ["+key+", "+value+"]");
    }

    public static Boolean getBoolean(Context context, String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.d(TAG, "getBoolean: ["+key+", "+sharedPreferences.getBoolean(key, false)+"]");

        return sharedPreferences.getBoolean(key, false);  //默认值设为false
    }

}
