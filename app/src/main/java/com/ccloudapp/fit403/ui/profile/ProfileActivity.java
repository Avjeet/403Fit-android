package com.ccloudapp.fit403.ui.profile;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.ui.base.BaseActivity;
import com.ccloudapp.fit403.ui.home.NavigationHomeActivity;
import com.ccloudapp.fit403.ui.widgets.ArrayAdapterWithHint;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {

    @BindView(R.id.area_of_foucs_spinner)
    Spinner mAreaOfFocusSpinner;
    @BindView(R.id.workout_type_spinner)
    Spinner mWorkoutTypeSpinner;
    @BindView(R.id.workout_style_spinner)
    Spinner mWorkoutStyleSpinner;
    @BindView(R.id.profile_title_textview)
    TextInputEditText mProfileTitleTextView;
    @BindView(R.id.profile_description_textview)
    TextInputEditText mProfileDescriptionTextView;

    @BindView(R.id.save_profile_floating_button)
    FloatingActionButton mSaveFloatingActionButton;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private String mAreaOfFocus;
    private String mWorkoutType;
    private String mWorkoutStyle;

    ProgressDialog mProgressDialog;

    @Inject
    ProfilePresenterImpl mProfilePresenter;

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        mProfilePresenter.attachView(this);
        initializeUi();
    }

    private void initializeUi() {
        mSaveFloatingActionButton.hide();
        setSupportActionBar(mToolbar);
        setTitle("Update profile");
        mToolbar.setNavigationIcon(R.drawable.ic_clear_black_24dp);
        mProfileTitleTextView.addTextChangedListener(mTextWatcher);
        mProfileDescriptionTextView.addTextChangedListener(mTextWatcher);
        ArrayAdapterWithHint<String> areaFocusAdapter = new ArrayAdapterWithHint<>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.area_focus));
        mAreaOfFocusSpinner.setAdapter(areaFocusAdapter);
        mAreaOfFocusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mAreaOfFocus = (String) parent.getItemAtPosition(position);
                    checkAndEnable();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAreaOfFocus = "";
            }
        });
        ArrayAdapterWithHint<String> workoutTypeAdapter = new ArrayAdapterWithHint<>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.workout_type));
        mWorkoutTypeSpinner.setAdapter(workoutTypeAdapter);
        mWorkoutTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mWorkoutType = (String) parent.getItemAtPosition(position);
                    checkAndEnable();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mWorkoutType = "";
            }
        });
        ArrayAdapterWithHint<String> workoutStyleAdapter = new ArrayAdapterWithHint<>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.workout_style));
        mWorkoutStyleSpinner.setAdapter(workoutStyleAdapter);
        mWorkoutStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    mWorkoutStyle = (String) parent.getItemAtPosition(position);
                    checkAndEnable();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mWorkoutStyle = "";
            }
        });
        mSaveFloatingActionButton.setOnClickListener(v -> {
            User user = new User();
            user.subject = mProfileTitleTextView.getText().toString().trim();
            user.description = mProfileDescriptionTextView.getText().toString().trim();
            user.areaOfFocus = mAreaOfFocus;
            user.workoutStyle = mWorkoutStyle;
            user.workoutType = mWorkoutType;
            mProfilePresenter.updateProfile(user);
        });




    }

    @Override
    public void showProgressDialog(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        Snackbar.make(mProfileTitleTextView, msg, LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMsg(String msg) {
        Log.d(TAG, "showSuccessMsg() called with: msg = [" + msg + "]");
    }

    @Override
    public void showDummyActivity() {
        startActivity(new Intent(this, NavigationHomeActivity.class));
    }

    @Override
    protected void onDestroy() {
        mProfilePresenter.detachView();
        super.onDestroy();
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                mSaveFloatingActionButton.hide();
            } else {
                checkAndEnable();
            }
        }
    };

    private void checkAndEnable() {
        if (!TextUtils.isEmpty(mProfileTitleTextView.getText().toString().trim())
                && !TextUtils.isEmpty(mProfileDescriptionTextView.getText().toString().trim())
                && !TextUtils.isEmpty(mAreaOfFocus)
                && !TextUtils.isEmpty(mWorkoutType)
                && !TextUtils.isEmpty(mWorkoutStyle)){
            mSaveFloatingActionButton.show();
        }
        else
            mSaveFloatingActionButton.hide();
    }
}
