package com.ccloudapp.fit403.ui.menu_activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.ui.base.BaseActivity;
import com.ccloudapp.fit403.ui.home.NavigationHomeActivity;
import com.ccloudapp.fit403.ui.users.BrowseUsersPresenterImpl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackWorkoutActivity extends NavigationHomeActivity implements TrackWorkoutContract.View{
    private static final String TAG = "TrackWorkoutActivity";
    @Inject
    TrackWorkoutPresenterImpl trackWorkoutPresenter;

    @Inject
    WorkoutAdapter workoutAdapter;

    @BindView(R.id.users_recycler_view)
    RecyclerView mUsersRecyclerView;

    @BindView(R.id.loading_spinner)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_track_workout);
        ButterKnife.bind(this);

        mUsersRecyclerView.setAdapter(workoutAdapter);
        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        trackWorkoutPresenter.attachView(this);
        trackWorkoutPresenter.showExercises();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_TRACK_WORKOUT;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMsg(String msg) {
        Snackbar.make(findViewById(R.id.main_content), msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMsg(String msg) {
        Log.d(TAG, "showSuccessMsg() called with: msg = [" + msg + "]");
    }

    @Override
    public void showWorkouts(List<Workout> workoutList) {

    }
}
