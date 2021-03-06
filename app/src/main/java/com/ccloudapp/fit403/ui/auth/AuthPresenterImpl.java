package com.ccloudapp.fit403.ui.auth;

import android.content.Context;
import android.util.Log;

import com.ccloudapp.fit403.FitnessApp;
import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.model.ApiError;
import com.ccloudapp.fit403.data.model.AuthResponse;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.di.context.ActivityContext;
import com.ccloudapp.fit403.ui.base.BasePresenterImpl;
import com.ccloudapp.fit403.util.RxUtil;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by dev on 12/8/17.
 */

public class AuthPresenterImpl extends BasePresenterImpl<AuthContract.View> implements
        AuthContract.Presenter {


    private final DataManager mDataManager;
    private Subscription mSubscription;
    private static final String TAG = "AuthPresenterImpl";

    @Inject
    public AuthPresenterImpl(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onLogin(String username, String password) {
        checkViewAttached();

        mSubscription = mDataManager.login(username, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showProgressDialog("Loading"))
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().dismissProgressDialog();
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Gson gson = new Gson();
                            try {
                                ApiError error = gson.fromJson(body.string(), ApiError.class);
                                getView().showErrorMsg(error.getMessage());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                getView().showErrorMsg(e1.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onNext(User user) {
                        Log.i(TAG, "onNext : " + user.username);
                        getView().dismissProgressDialog();
                        getView().showDummyActivity();
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
