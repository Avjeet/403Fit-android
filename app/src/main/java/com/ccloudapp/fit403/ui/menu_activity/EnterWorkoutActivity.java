package com.ccloudapp.fit403.ui.menu_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.ExerciseName;
import com.ccloudapp.fit403.data.model.Workout;
import com.ccloudapp.fit403.ui.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

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

    @BindView(R.id.body_weight_edit)
    EditText bodyWeightEdit;

    @BindView(R.id.weight_edit)
    EditText weightEdit;

    @BindView(R.id.reps_edit)
    EditText reps_edit;

    @BindView(R.id.sets_edit)
    EditText sets_edit;

    @BindView(R.id.failed_sets_edit)
    EditText failed_sets;

    @BindView(R.id.calories_edit)
    EditText caloriesEdit;

    @BindView(R.id.rest_edit)
    EditText restEdit;

    @BindView(R.id.tempo_edit)
    EditText tempoEdit;

    @BindView(R.id.add_btn)
    Button addBtn;

    @BindView(R.id.cancel_button)
    Button cancelBtn;

    private ArrayList<String> nameList;
    private Workout workout;

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

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        addBtn.setOnClickListener(view -> {
            workout= new Workout();
            workout.body_weight=Integer.parseInt(bodyWeightEdit.getText().toString().trim());
            workout.calories=Integer.parseInt(caloriesEdit.getText().toString().trim());
            workout.exercise_category=edittext_category.getText().toString().trim();
            workout.sets=Integer.parseInt(sets_edit.getText().toString().trim());
            workout.reps=Integer.parseInt(reps_edit.getText().toString().trim());
            workout.failed_set=Integer.parseInt(failed_sets.getText().toString().trim());
            workout.rest=Integer.parseInt(restEdit.getText().toString().trim());
            workout.date=df.format(Calendar.getInstance().getTime());
            workout.image_url=getIntent().getStringExtra("ex_img");
            workout.exercise_name=nameSpinner.getSelectedItem().toString();
            workout.weight=Integer.parseInt(weightEdit.getText().toString().trim());
            workout.tempo=tempoEdit.getText().toString().trim();

            enterWorkoutPresenter.postData(workout);
        });




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

    @Override
    public void workoutPostComplete(Boolean completed) {
        Intent returnIntent = new Intent();
        if(completed){
            Toast.makeText(this, "Workout Posted Successfully", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK, returnIntent);
        }
        else{
            Toast.makeText(this, "Workout Can't be Posted Successfully", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();
    }


}
