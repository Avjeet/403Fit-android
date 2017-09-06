package com.ccloudapp.fit403.ui.menu_activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;


import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.ExerciseName;
import com.ccloudapp.fit403.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterWorkoutActivity extends BaseActivity implements EnterWorkoutContract.View{
    private static final String TAG = "EnterWorkoutActivity";

    @Inject
    EnterWorkoutPresenterImpl enterWorkoutPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.category_edit)
    EditText edittext_category;

    @BindView(R.id.name_spinner)
    Spinner nameSpinner;


    private ArrayList<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_workout);
        ButterKnife.bind(this);
        activityComponent().inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        enterWorkoutPresenter.attachView(this);
        enterWorkoutPresenter.fetchExerciseName(getIntent().getStringExtra("ex_id"));


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
    public void setDataInSpinner(List<ExerciseName> listName) {
        nameList = new ArrayList<>();
        for(int i = 0;i<listName.size();i++){
            nameList.add(listName.get(i).exercise_name);
        }

        nameSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList));

        edittext_category.setText(listName.get(0).category);


    }
}
