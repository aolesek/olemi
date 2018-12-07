package pl.edu.agh.student.olemi;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import pl.edu.agh.student.olemi.logging.DebugLogTree;
import timber.log.Timber;

public class OlemiApplication extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Timber.plant(new DebugLogTree());
        Timber.i("Application started");
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}