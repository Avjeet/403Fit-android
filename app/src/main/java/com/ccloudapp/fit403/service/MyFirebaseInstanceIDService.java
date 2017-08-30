package com.ccloudapp.fit403.service;

import android.app.Service;
import android.util.Log;

import com.ccloudapp.fit403.FitnessApp;
import com.ccloudapp.fit403.data.DataManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

/**
 * Created by Amit on 29/8/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIIDService";

    @Inject
    DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        FitnessApp.get(this).getApplicationComponent().inject(this);
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
