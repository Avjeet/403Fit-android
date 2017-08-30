package com.ccloudapp.fit403.ui.users;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseActivity implements UserProfileContract.View{

    @BindView(R.id.profile_image)
    ImageView mImageView;
    @BindView(R.id.user_name_tv)
    TextView mUsernameTextView;
    @BindView(R.id.user_subject_tv)
    TextView mUserSubjectTextView;
    @BindView(R.id.user_description_tv)
    TextView mUserDescriptionTextView;
    @BindView(R.id.loading_spinner)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_content)
    ScrollView mScrollView;

    @BindView(R.id.bottom_panel)
    LinearLayout mLinearLayout;

    @Inject
    UserProfilePresenterImpl mUserProfilePresenter;

    private static final String TAG = "UserProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        mUserProfilePresenter.attachView(this);
        mLinearLayout.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
        Intent intent = getIntent();
        if(intent.getStringExtra("USER_ID") != null){
            mUserProfilePresenter.getUser(intent.getStringExtra("USER_ID"));
        }
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMsg(String msg) {
        Snackbar.make(mImageView, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMsg(String msg) {
        Log.d(TAG, "showSuccessMsg() called with: msg = [" + msg + "]");
    }

    @Override
    public void showUsers(User user) {
        mScrollView.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.face).into(mImageView);
        mUsernameTextView.setText(user.username);
        mUserSubjectTextView.setText((user.subject != null) ? user.subject : user.username+" has not added any subject yet.");
        mUserDescriptionTextView.setText((user.description != null) ? user.description :  user.username+" has not added any description yet.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserProfilePresenter.detachView();
    }
}
