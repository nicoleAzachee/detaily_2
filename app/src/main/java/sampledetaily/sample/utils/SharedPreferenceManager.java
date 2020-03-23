package sampledetaily.sample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import sampledetaily.sample.DetailyApplication;
import sampledetaily.sample.data.Song;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceManager {

    public static final String TAG = SharedPreferenceManager.class.getSimpleName();
    public Context context;
    public Activity activity;

    public SharedPreferenceManager(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void saveStringSP(String key, String value){
        Log.d(TAG, "== 2 == saveStringSP NICOLE: ");

        SharedPreferences preferences = activity.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringSP(String key){
        SharedPreferences preferences = activity.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String name = preferences.getString(key, "");
        return name;
    }

    public void saveSongSP(String key, Song song){
        Gson gson = new Gson();
        String json = gson.toJson(song);
        SharedPreferences preferences = activity.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public Song getSongSP(String key){
        Gson gson = new Gson();
        SharedPreferences preferences = activity.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String json = preferences.getString(key, "");
        Song song = gson.fromJson(json, Song.class);
        return song;
    }
}
