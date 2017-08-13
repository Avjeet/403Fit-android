package com.ccloudapp.fit403.ui.auth;

import android.content.Context;
import android.util.Log;

import com.ccloudapp.fit403.FitnessApp;
import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.model.AuthResponse;
import com.ccloudapp.fit403.di.context.ActivityContext;
import com.ccloudapp.fit403.ui.base.BasePresenterImpl;
import com.ccloudapp.fit403.util.RxUtil;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dev on 12/8/17.
 */

public class AuthPresenterImpl extends BasePresenterImpl<AuthContract.View> implements AuthContract.Presenter {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private static final String TAG = "AuthPresenterImpl";

    public AuthPresenterImpl(@ActivityContext Context loginActivity){
        mDataManager = FitnessApp.get(loginActivity).getApplicationComponent().dataManager();
    }

    @Override
    public void onLogin(String username, String password) {
        mSubscription = mDataManager.login(username, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        Log.i(TAG, "onNext: "+authResponse.toString());
                    }
                });
    }

    @Override
    public void attachView(AuthContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        RxUtil.unsubscribe(mSubscription);
        super.detachView();
    }
}
