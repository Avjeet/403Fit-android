package com.ccloudapp.fit403.ui.auth;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.ui.base.BaseActivity;
import com.ccloudapp.fit403.ui.profile.ProfileActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity implements BirthdayDialogFragment.Listener,
        SignupContract.View {

    @BindView(R.id.dob_edit_text)
    EditText mDobEditText;
    @BindView(R.id.email_edit_text)
    EditText mEmailEditText;
    @BindView(R.id.user_name_edit_text)
    EditText mNameEditText;
    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;
    @BindView(R.id.gender_radio_group)
    RadioGroup mGenderRadioGroup;
    @BindView(R.id.register_button)
    Button mRegisterBtn;
    ProgressDialog mProgressDialog;

    @Inject
    SignupPresenterImpl mSignupPresenter;

    private static final String TAG = "SignupActivity";
    private String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        mSignupPresenter.attachView(this);
        mRegisterBtn.setOnClickListener(v -> {
            if (isAllFieldsValid()) {
                User user = new User();
                user.username = mNameEditText.getText().toString().trim();
                user.email = mEmailEditText.getText().toString().trim();
                user.dob = formattedDate;
                user.gender = mGenderRadioGroup.getCheckedRadioButtonId() == R.id.male_radio_button
                        ? "Male" : "Female";
                user.password = mPasswordEditText.getText().toString().trim();
                mSignupPresenter.register(user);
            } else {
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.dob_edit_text)
    public void onDobClick() {
        DialogFragment dialogFragment = BirthdayDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "birthday");
    }

    @Override
    public void onDateSelectListener(String selectedDate, String formattedDate) {
        this.formattedDate = formattedDate;
        mDobEditText.setText(selectedDate);

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
        Snackbar.make(mEmailEditText, msg, LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMsg(String msg) {
        Log.d(TAG, "showSuccessMsg() called with: msg = [" + msg + "]");
    }

    @Override
    public void showDummyActivity(String username, String email) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignupPresenter.detachView();
    }

    public boolean isAllFieldsValid() {
        return !TextUtils.isEmpty(mEmailEditText.getText())
                && !TextUtils.isEmpty(mDobEditText.getText())
                && !TextUtils.isEmpty(mNameEditText.getText())
                && !TextUtils.isEmpty(mPasswordEditText.getText())
                && (mGenderRadioGroup.getCheckedRadioButtonId() != -1);
    }
}
