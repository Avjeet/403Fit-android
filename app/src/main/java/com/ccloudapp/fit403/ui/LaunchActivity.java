package com.ccloudapp.fit403.ui;

import android.content.Intent;
import android.os.Bundle;

import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.ui.auth.LoginActivity;
import com.ccloudapp.fit403.ui.base.BaseActivity;
import com.ccloudapp.fit403.ui.home.NavigationHomeActivity;
import com.ccloudapp.fit403.ui.users.BrowseUsersActivity;

import javax.inject.Inject;

public class LaunchActivity extends BaseActivity {

    @Inject
    DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        if(mDataManager.getPreferenceHelper().hasActiveAccount()){
            startActivity(new Intent(this, BrowseUsersActivity.class));
            finish();
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
