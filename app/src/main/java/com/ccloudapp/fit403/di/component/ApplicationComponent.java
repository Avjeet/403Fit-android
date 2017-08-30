package com.ccloudapp.fit403.di.component;

import android.app.Application;
import android.content.Context;

import com.ccloudapp.fit403.FitnessApp;
import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.local.db.DatabaseHelper;
import com.ccloudapp.fit403.data.local.prefs.PreferencesHelper;
import com.ccloudapp.fit403.data.network.FitnessRestClient;
import com.ccloudapp.fit403.di.context.ApplicationContext;
import com.ccloudapp.fit403.di.module.ActivityModule;
import com.ccloudapp.fit403.di.module.ApplicationModule;
import com.ccloudapp.fit403.di.scopes.ApplicationScoped;
import com.ccloudapp.fit403.service.MyFirebaseInstanceIDService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by amit on 3/2/17.
 */

@ApplicationScoped
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(FitnessApp fitnessApp);

    @ApplicationContext
    Context context();

    Application application();

    PreferencesHelper preferencesHelper();

    DatabaseHelper databaseHelper();

    FitnessRestClient fitApi();

    DataManager dataManager();

    ActivityComponent plus(ActivityModule activityModule);

    void inject(MyFirebaseInstanceIDService myFirebaseInstanceIDService);
}
