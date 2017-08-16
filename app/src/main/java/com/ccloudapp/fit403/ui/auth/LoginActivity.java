package com.ccloudapp.fit403.ui.auth;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.ui.DummyActivity;
import com.ccloudapp.fit403.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements AuthContract.View {

    @BindView(R.id.user_name_edit_text)
    EditText mUsernameEditText;

    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.create_account_tv)
    TextView mCreateAccountTextView;

    @Inject
    AuthPresenterImpl mAuthPresenter;

    ProgressDialog mProgressDialog;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityComponent().inject(this);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        mAuthPresenter.attachView(this);
        mLoginButton.setOnClickListener(
                v -> mAuthPresenter.onLogin(mUsernameEditText.getText().toString().trim(),
                        mPasswordEditText.getText().toString().trim()));
        mCreateAccountTextView.setOnClickListener(v -> {
            //todo: go to signup activity
            Toast.makeText(this, "This feature will be added when signup api method is done.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "create account tv");
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
        Snackbar.make(mUsernameEditText, msg, LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMsg(String msg) {
        Log.d(TAG, "showSuccessMsg() called with: msg = [" + msg + "]");
    }

    @Override
    public void showDummyActivity() {
        startActivity(new Intent(this, DummyActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuthPresenter.detachView();
    }
}
