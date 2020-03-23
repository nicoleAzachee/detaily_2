package sampledetaily.sample.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import sampledetaily.sample.data.EnvironmentVariables;
import sampledetaily.sample.data.Song;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceManager {

    public static final String TAG = SharedPreferenceManager.class.getSimpleName();
    public Context context;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SharedPreferenceManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void saveStringSP(String key, String value) {
        Log.d(TAG, "== 2 == saveStringSP NICOLE: ");
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringSP(String key) {
        String name = preferences.getString(key, "");
        return name;
    }

    public void saveSongSP(String key, Song song) {
        Gson gson = new Gson();
        String json = gson.toJson(song);
        editor.putString(key, json);
        editor.apply();
    }

    public Song getSongSP(String key) {
        Gson gson = new Gson();
        String json = preferences.getString(key, "");
        Song song = gson.fromJson(json, Song.class);
        return song;
    }

    public void saveLastSeenTime(long epochTime) {
        editor.putLong(EnvironmentVariables.LAST_LOGIN_TIME, System.currentTimeMillis());
        editor.apply();
    }

    public long getLastSeenTime() {
        long lastLoginTime = preferences.getLong(EnvironmentVariables.LAST_LOGIN_TIME, 0L);
        return lastLoginTime;
    }

    public void saveLastUserActivity(int userActivity) {
        editor.putInt(EnvironmentVariables.LAST_USER_ACTIVITY, userActivity);
        editor.apply();
    }

    public int getLastUserActivity() {
        int lastUserActivity = preferences.getInt(EnvironmentVariables.LAST_USER_ACTIVITY, -1);
        return lastUserActivity;
    }
}
