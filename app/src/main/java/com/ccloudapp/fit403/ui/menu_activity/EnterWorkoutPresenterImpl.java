package com.ccloudapp.fit403.ui.menu_activity;

import android.widget.Toolbar;

import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.data.model.ExerciseName;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.ui.base.BasePresenterImpl;
import com.ccloudapp.fit403.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Completable;
import rx.CompletableSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by AVJEET on 05-09-2017.
 */

public class EnterWorkoutPresenterImpl extends BasePresenterImpl<EnterWorkoutContract.View>
        implements EnterWorkoutContract.Presenter {

    private final DataManager mDataManager;
    private Subscription mSubscription;


    @Inject
    EnterWorkoutPresenterImpl(DataManager dataManager){
        mDataManager=dataManager;
    }

    @Override
    public void attachView(EnterWorkoutContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {
        super.detachView();
        RxUtil.unsubscribe(mSubscription);
    }

    @Override
    public void fetchExerciseName(String id) {
        mSubscription=mDataManager.getExerciseName(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ExerciseName>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ExerciseName> exerciseNames) {
                        getView().setDataInSpinner(exerciseNames);
                    }
                });
    }

    @Override
    public void postData(Workout workout) {
        mDataManager.postNewWorkout(workout)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableSubscriber() {
                    @Override
                    public void onCompleted() {getView().workoutPostComplete(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().workoutPostComplete(false);
                    }

                    @Override
                    public void onSubscribe(Subscription d) {

                    }
                });
    }


}
