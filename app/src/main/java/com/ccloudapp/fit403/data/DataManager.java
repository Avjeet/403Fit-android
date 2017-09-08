package com.ccloudapp.fit403.data;

import com.ccloudapp.fit403.data.local.db.DatabaseHelper;
import com.ccloudapp.fit403.data.local.prefs.PreferencesHelper;
import com.ccloudapp.fit403.data.model.Credentials;
import com.ccloudapp.fit403.data.model.ExerciseName;
import com.ccloudapp.fit403.data.model.Exercise_category;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.data.network.FitnessRestClient;
import com.ccloudapp.fit403.data.network.model.RequestFriend;
import com.ccloudapp.fit403.data.network.model.ResponseFriendRequest;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.Subscription;

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

    public Observable<User> login(String email, String password) {
        Credentials credentials = new Credentials();
        credentials.email = email;
        credentials.password = password;
        return lazyFitnessRestClient.get().login(credentials).doOnNext(
                user -> {
                    mPreferencesHelper.setActiveAccountToken(user.token);
                    mPreferencesHelper.setEmail(user.email);
                    mPreferencesHelper.setName(user.username);
                    mPreferencesHelper.setGender(user.gender);
                    mPreferencesHelper.setDob(user.dob);
                    mPreferencesHelper.setSubject(user.subject);
                    mPreferencesHelper.setWorkoutType(user.workoutType);
                    mPreferencesHelper.setWorkoutStyle(user.workoutStyle);
                    mPreferencesHelper.setAreaOfFocus(user.areaOfFocus);
                    mPreferencesHelper.setDescription(user.description);
                });
    }

    public Observable<User> register(User user) {
        return lazyFitnessRestClient.get().register(user)
                .doOnNext(user1 -> {
                    mPreferencesHelper.setActiveAccountToken(user1.token);
                    mPreferencesHelper.setEmail(user1.email);
                    mPreferencesHelper.setName(user1.username);
                    mPreferencesHelper.setGender(user1.gender);
                    mPreferencesHelper.setDob(user1.dob);
                });
    }

    public Observable<List<UserPublic>> getAllUsers() {
        return lazyFitnessRestClient.get().getUsers(
                "Bearer " + mPreferencesHelper.getActiveAccountToken());
    }

    public Observable<User> getUserById(String userId) {
        return lazyFitnessRestClient.get().getUser(
                "Bearer " + mPreferencesHelper.getActiveAccountToken(), userId);
    }

    public Completable updateProfile(User user) {
        return lazyFitnessRestClient.get().updateProfile(
                "Bearer " + mPreferencesHelper.getActiveAccountToken(), user);
    }

    public Observable<ResponseFriendRequest> addFriend(String userId){
        RequestFriend requestFriend = new RequestFriend();
        requestFriend.to_friend = userId;
        requestFriend.action = "add_friend";
        return lazyFitnessRestClient.get().addFriend("Bearer " + mPreferencesHelper.getActiveAccountToken(), requestFriend);
    }
    public Observable<ResponseFriendRequest> confirmFriend(String userId){
        RequestFriend requestFriend = new RequestFriend();
        requestFriend.to_friend = userId;
        requestFriend.action = "confirm_friend";
        return lazyFitnessRestClient.get().addFriend("Bearer " + mPreferencesHelper.getActiveAccountToken(), requestFriend);
    }
    public Observable<ResponseFriendRequest> declineFriend(String userId){
        RequestFriend requestFriend = new RequestFriend();
        requestFriend.to_friend = userId;
        requestFriend.action = "decline_friend";
        return lazyFitnessRestClient.get().addFriend("Bearer " + mPreferencesHelper.getActiveAccountToken(), requestFriend);
    }
    public Observable<List<Workout>> getPreviousWorkouts(){
        return lazyFitnessRestClient.get().getPreviousWorkouts("Bearer "+mPreferencesHelper.getActiveAccountToken());
    }

    public Single<List<Exercise_category>> getExercisescategory(){
        return lazyFitnessRestClient.get().getExerciseCategory("Bearer "+mPreferencesHelper.getActiveAccountToken());
    }

    public Single<List<ExerciseName>> getExerciseName(String id){
        return lazyFitnessRestClient.get().getExerciseName("Bearer "+mPreferencesHelper.getActiveAccountToken(),id);
    }

    public Completable postNewWorkout(Workout workout){
        return lazyFitnessRestClient.get().postNewWorkout("Bearer "+mPreferencesHelper.getActiveAccountToken(),workout);
    }


}
