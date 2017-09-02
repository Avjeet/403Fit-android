package com.ccloudapp.fit403.ui.menu_activity;

import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.ui.base.BasePresenter;
import com.ccloudapp.fit403.ui.base.BaseView;

import java.util.List;

/**
 * Created by AVJEET on 02-09-2017.
 */

public class TrackWorkoutContract {
    interface View extends BaseView{

        void showProgressBar();

        void dismissProgressBar();

        void showErrorMsg(String msg);

        void showSuccessMsg(String msg);

        void showWorkouts(List<Workout> workoutList);

    }

    interface Presenter extends BasePresenter<View>{
        void showExercises();
    }
}
