package com.ccloudapp.fit403.ui.auth;

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
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by dev on 26/8/17.
 */

public class SignupPresenterImpl extends BasePresenterImpl<SignupContract.View> implements
        SignupContract.Presenter {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SignupPresenterImpl(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void register(User user) {
        checkViewAttached();
        mSubscription = mDataManager.register(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> getView().showProgressDialog("Signing up"))
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
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
                        getView().dismissProgressDialog();
                        getView().showDummyActivity(user.username, user.email);
                    }
                });
    }

    @Override
    public void attachView(SignupContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        RxUtil.unsubscribe(mSubscription);
        super.detachView();
    }
}
