package com.ccloudapp.fit403.ui.profile;

import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.model.ApiError;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.ui.base.BasePresenterImpl;
import com.ccloudapp.fit403.util.RxUtil;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.CompletableSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Amit on 27/8/17.
 */

public class ProfilePresenterImpl extends BasePresenterImpl<ProfileContract.View> implements ProfileContract.Presenter {


    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ProfilePresenterImpl(DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void updateProfile(User user) {
        checkViewAttached();
        getView().showProgressDialog("Saving profile...");
        mDataManager.updateProfile(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableSubscriber() {
                    @Override
                    public void onCompleted() {
                        getView().dismissProgressDialog();
                        getView().showDummyActivity();
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
                    public void onSubscribe(Subscription d) {
                    }
                });
    }

    @Override
    public void attachView(ProfileContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        RxUtil.unsubscribe(mSubscription);
        super.detachView();
    }
}
