package com.ccloudapp.fit403.ui.users;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.ui.base.BaseActivity;
import com.ccloudapp.fit403.ui.home.NavigationHomeActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseUsersActivity extends NavigationHomeActivity implements BrowseUsersContract.View{


    @Inject
    BrowseUsersPresenterImpl mBrowseUsersPresenter;

    @BindView(R.id.users_recycler_view)
    RecyclerView mUsersRecyclerView;

    @BindView(R.id.loading_spinner)
    ProgressBar mProgressBar;

    @Inject
    UsersAdapter mUsersAdapter;

    private static final String TAG = "BrowseUsersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_browse_users);
        ButterKnife.bind(this);

        mUsersRecyclerView.setAdapter(mUsersAdapter);
        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBrowseUsersPresenter.attachView(this);
        mBrowseUsersPresenter.browseUsers();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_BROSWE_PEOPLE;
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
    public void showUsers(List<UserPublic> list) {
        mUsersAdapter.setData(list);
        mUsersAdapter.notifyDataSetChanged();
    }

    public void openProfileActivity(String userId){
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }
}
