package com.ccloudapp.fit403.di.component;


import com.ccloudapp.fit403.di.module.ActivityModule;
import com.ccloudapp.fit403.di.scopes.ActivityScoped;
import com.ccloudapp.fit403.ui.LaunchActivity;
import com.ccloudapp.fit403.ui.auth.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by amit on 3/2/17.
 * This component inject dependencies to all Activities across the application
 */
@ActivityScoped
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
    void inject(LaunchActivity launchActivity);
}
