package com.ccloudapp.fit403.ui.menu_activity;

import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.model.ApiError;
import com.ccloudapp.fit403.data.model.Exercise_category;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.ui.base.BasePresenterImpl;
import com.ccloudapp.fit403.util.RxUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by AVJEET on 02-09-2017.
 */

public class TrackWorkoutPresenterImpl extends BasePresenterImpl<TrackWorkoutContract.View> implements
        TrackWorkoutContract.Presenter {

    private final DataManager mDataManager;
    private Subscription mSubscription,nSubscription;

    @Inject
    public TrackWorkoutPresenterImpl(DataManager datamanager) {
        mDataManager = datamanager;
    }

    @Override
    public void attachView(TrackWorkoutContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
        RxUtil.unsubscribe(mSubscription);
    }

    @Override
    public void showExercises() {
        checkViewAttached();
        getView().showProgressBar();
        mSubscription = mDataManager.getPreviousWorkouts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Workout>>() {
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
                    public void onNext(List<Workout> workouts) {
                        getView().dismissProgressBar();
                        getView().showWorkouts(workouts);
                    }
                });
    }

    @Override
    public void fetchExercises() {
        nSubscription=mDataManager.getExercisescategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Exercise_category>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Exercise_category> exercise_categories) {
                        getView().openDialog(exercise_categories);
                    }
                });
    }

}
