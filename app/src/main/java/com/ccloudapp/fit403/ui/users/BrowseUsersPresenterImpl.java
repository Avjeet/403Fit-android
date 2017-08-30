package com.ccloudapp.fit403.ui.users;

import android.widget.ScrollView;

import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.model.ApiError;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.data.network.model.ResponseFriendRequest;
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

public class BrowseUsersPresenterImpl extends BasePresenterImpl<BrowseUsersContract.View> implements
        BrowseUsersContract.Presenter {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public BrowseUsersPresenterImpl(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(BrowseUsersContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        RxUtil.unsubscribe(mSubscription);
        super.detachView();
    }

    @Override
    public void browseUsers() {
        checkViewAttached();
        getView().showProgressBar();
        mSubscription = mDataManager.getAllUsers().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<UserPublic>>() {
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
                    public void onNext(List<UserPublic> userPublics) {
                        Collections.sort(userPublics);
                        getView().dismissProgressBar();
                        getView().showUsers(userPublics);
                    }
                });
    }

    @Override
    public void acceptRequest(String userId, int position) {
        checkViewAttached();
        mSubscription = mDataManager.confirmFriend(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseFriendRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().revertView(position);
                    }

                    @Override
                    public void onNext(ResponseFriendRequest responseFriendRequest) {

                    }
                });
    }

    @Override
    public void ignoreRequest(String userId, int position) {
        checkViewAttached();
        mSubscription = mDataManager.declineFriend(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseFriendRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().revertView(position);
                    }

                    @Override
                    public void onNext(ResponseFriendRequest responseFriendRequest) {
                        getView().showSuccessMsg("Friend request sent");
                    }
                });
    }

    @Override
    public void addFriend(String userId, int position) {
        checkViewAttached();
        mSubscription = mDataManager.addFriend(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseFriendRequest>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().revertView(position);
                    }

                    @Override
                    public void onNext(ResponseFriendRequest responseFriendRequest) {

                    }
                });
    }
}
