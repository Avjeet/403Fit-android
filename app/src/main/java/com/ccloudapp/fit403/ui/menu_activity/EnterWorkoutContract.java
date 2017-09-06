package com.ccloudapp.fit403.ui.menu_activity;

import com.ccloudapp.fit403.data.model.ExerciseName;
import com.ccloudapp.fit403.ui.base.BasePresenter;
import com.ccloudapp.fit403.ui.base.BaseView;

import java.util.List;

/**
 * Created by AVJEET on 05-09-2017.
 */

public class EnterWorkoutContract {
    interface View extends BaseView {


        void showErrorMsg(String msg);

        void showSuccessMsg(String msg);

        void setDataInSpinner(List<ExerciseName> listName);



    }

    interface Presenter extends BasePresenter<EnterWorkoutContract.View> {
        void fetchExerciseName(String id);

        void postData();

    }
}
