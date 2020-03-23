package sampledetaily.sample.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import sampledetaily.sample.DetailyApplication;
import sampledetaily.sample.data.Song;

public class SharedPreferenceManager {

    public static final String TAG = SharedPreferenceManager.class.getSimpleName();
    public static void saveStringSP(String key, String value){
        Log.d(TAG, "== 2 == saveStringSP NICOLE: ");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DetailyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringSP(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DetailyApplication.getAppContext());
        String name = preferences.getString(key, "");
        return name;
    }

    public static void saveSongSP(String key, Song song){
        Gson gson = new Gson();
        String json = gson.toJson(song);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DetailyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public static Song getSongSP(String key){
        Gson gson = new Gson();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DetailyApplication.getAppContext());
        String json = preferences.getString(key, "");
        Song song = gson.fromJson(json, Song.class);
        return song;
    }
}
