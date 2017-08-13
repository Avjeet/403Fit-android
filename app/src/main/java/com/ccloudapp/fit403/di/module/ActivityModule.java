package com.ccloudapp.fit403.di.module;

import android.app.Activity;
import android.content.Context;

import com.ccloudapp.fit403.di.context.ActivityContext;
import com.ccloudapp.fit403.di.scopes.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amit on 3/2/17.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityScoped
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    @ActivityScoped
    Context providesContext() {
        return mActivity;
    }
}
