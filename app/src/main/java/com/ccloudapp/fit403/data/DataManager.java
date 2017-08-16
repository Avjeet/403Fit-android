package com.ccloudapp.fit403.data;

import android.util.Log;

import com.ccloudapp.fit403.data.local.db.DatabaseHelper;
import com.ccloudapp.fit403.data.local.prefs.PreferencesHelper;
import com.ccloudapp.fit403.data.model.AuthRequest;
import com.ccloudapp.fit403.data.model.AuthResponse;
import com.ccloudapp.fit403.data.network.FitnessRestClient;
import com.evernote.android.job.JobRequest;
import com.evernote.android.job.util.support.PersistableBundleCompat;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by amit on 31/12/16.
 *
 * @author amit
 */
@Singleton
public class DataManager {

    @Inject
    Lazy<FitnessRestClient> lazyFitnessRestClient;
    @Inject
    Lazy<DatabaseHelper> lazyDatabaseHelper;

    private final PreferencesHelper mPreferencesHelper;

    private static final String TAG = "DataManager";


    @Inject
    public DataManager(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferenceHelper() {
        return mPreferencesHelper;
    }

    public FitnessRestClient getApi() {
        return lazyFitnessRestClient.get();
    }

    //TODO: Add data layer interactions here.

    public Observable<AuthResponse> login(String username, String password) {
        return lazyFitnessRestClient.get().login(username, password).doOnNext(
                authResponse -> {
                    mPreferencesHelper.setActiveAccountToken(authResponse.getAccessToken());
                });
    }
}
