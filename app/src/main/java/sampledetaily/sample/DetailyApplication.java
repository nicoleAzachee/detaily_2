package sampledetaily.sample;

import android.app.Application;
import android.content.Context;

public class DetailyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        DetailyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return DetailyApplication.context;
    }
}