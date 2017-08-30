package com.ccloudapp.fit403.ui.users;

import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.model.ApiError;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.ui.base.BasePresenterImpl;
import com.ccloudapp.fit403.util.RxUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Amit on 27/8/17.
 */

public class UserProfilePresenterImpl extends BasePresenterImpl<UserProfileContract.View> implements UserProfileContract.Presenter {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public UserProfilePresenterImpl(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(UserProfileContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        RxUtil.unsubscribe(mSubscription);
        super.detachView();
    }

    @Override
    public void getUser(String userId) {
        checkViewAttached();
        getView().showProgressBar();
        mSubscription = mDataManager.getUserById(userId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().dismissProgressBar();
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
                       getView().dismissProgressBar();
                       getView().showUsers(user);
                    }
                });
    }
}
