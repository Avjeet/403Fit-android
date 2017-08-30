package com.ccloudapp.fit403;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.di.component.ApplicationComponent;
import com.ccloudapp.fit403.di.component.DaggerApplicationComponent;
import com.ccloudapp.fit403.di.module.ApplicationModule;
import com.evernote.android.job.JobManager;

import javax.inject.Inject;

/**
 * Created by dev on 11/8/17.
 */

public class FitnessApp extends MultiDexApplication {

    private ApplicationComponent mApplicationComponent;
    private static boolean activityVisible;
    private static final String TAG = "DotApp";

    @Inject
    DataManager mDataManager;



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");

        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        mApplicationComponent.inject(this);

    }

    public static FitnessApp get(Context context) {
        return (FitnessApp) context.getApplicationContext();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public boolean isAuthorized() {
        return (mDataManager.getPreferenceHelper().getName() != null);
    }
}
