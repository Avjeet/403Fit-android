package com.ccloudapp.fit403.ui.menu_activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.Exercise_category;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.ui.base.BaseActivity;
import com.ccloudapp.fit403.ui.home.NavigationHomeActivity;
import com.ccloudapp.fit403.ui.users.BrowseUsersPresenterImpl;

import java.util.ArrayList;
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

    @BindView(R.id.add_floating_btn)
    FloatingActionButton add_btn;

    private Dialog dialog;
    private ListView dialoglistview;
    private ArrayList<String> title_list;

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

        add_btn.setOnClickListener(view -> trackWorkoutPresenter.fetchExercises());
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
        workoutAdapter.setData(workoutList);
        workoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void openDialog(List<Exercise_category> list) {
        title_list=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            title_list.add(list.get(i).getExercise_title());
        }

        dialog=new Dialog(this);
        dialog.setTitle("Choose Exercise");
        dialog.setContentView(R.layout.dialog_exercises_list);

        dialoglistview=(ListView) dialog.findViewById(R.id.dialoglist);
        dialoglistview.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, title_list));

        dialoglistview.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(TrackWorkoutActivity.this,EnterWorkoutActivity.class);
            intent.putExtra( "ex_id",list.get(i).getId());
            intent.putExtra("ex_name",list.get(i).getExercise_title());
            intent.putExtra("ex_img",list.get(i).getImg_url());
            startActivityForResult(intent,1);
            dialog.cancel();
        });
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trackWorkoutPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                trackWorkoutPresenter.showExercises();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
