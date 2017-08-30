package com.ccloudapp.fit403.ui.auth;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.ui.home.NavigationHomeActivity;
import com.ccloudapp.fit403.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements AuthContract.View {

    @BindView(R.id.email_edit_text)
    EditText mEmailEditText;

    @BindView(R.id.password_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.register_button)
    Button mRegisterButton;

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
                v -> mAuthPresenter.onLogin(mEmailEditText.getText().toString().trim(),
                        mPasswordEditText.getText().toString().trim()));
        mRegisterButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
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
        Snackbar.make(mEmailEditText, msg, LENGTH_SHORT).show();
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
        super.onDestroy();
        mAuthPresenter.detachView();
    }
}
