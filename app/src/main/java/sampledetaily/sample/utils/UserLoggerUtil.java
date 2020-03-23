package sampledetaily.sample.utils;

import android.content.Context;
import android.util.Log;

public class UserLoggerUtil {
    private static String TAG = UserLoggerUtil.class.getSimpleName();

    public static void updateLastActivity(Context context, int currentActivity) {
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
        sharedPreferenceManager.saveLastSeenTime(System.currentTimeMillis());
        sharedPreferenceManager.saveLastUserActivity(currentActivity);
        Log.i(TAG, "User Logged Time: " + sharedPreferenceManager.getLastSeenTime()
                + "on Activity: " + sharedPreferenceManager.getLastUserActivity());
    }

}
