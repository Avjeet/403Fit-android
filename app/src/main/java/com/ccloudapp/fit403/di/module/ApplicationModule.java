package com.ccloudapp.fit403.di.module;

import android.app.Application;
import android.content.Context;

import com.ccloudapp.fit403.data.network.FitnessRestClient;
import com.ccloudapp.fit403.di.context.ApplicationContext;
import com.ccloudapp.fit403.di.scopes.ApplicationScoped;
import com.ccloudapp.fit403.di.scopes.DatabaseName;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by amit on 3/2/17.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationScoped
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    @ApplicationScoped
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @DatabaseName
    String provideDatabaseName() {
        return "fit-db";
    }

    @Provides
    @Singleton
    FitnessRestClient provideDotApi() {
        return FitnessRestClient.Creator.makeFitApi(mApplication);
    }
}
