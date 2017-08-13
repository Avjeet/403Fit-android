package com.ccloudapp.fit403.ui.base;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ccloudapp.fit403.FitnessApp;
import com.ccloudapp.fit403.di.component.ActivityComponent;
import com.ccloudapp.fit403.di.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    private static final String TAG = "BaseActivity";
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = FitnessApp.get(this).getApplicationComponent().plus(new ActivityModule(this));
        }
        return mActivityComponent;
    }

    //TODO: Comment out after creating toolbar

    /*protected Toolbar getToolbar() {
        if (mToolBar == null) {
            mToolBar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolBar != null) {
                setSupportActionBar(mToolBar);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                applyFontForToolbarTitle(this);
            }
        }
        return mToolBar;
    }*/

    /*public static void applyFontForToolbarTitle(Activity context) {
        Toolbar toolbar = (Toolbar) context.findViewById(R.id.toolbar);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setTextSize(18);
                tv.setPadding(0, 0, 0, 0);
                Typeface titleFont = Typeface.
                        createFromAsset(context.getAssets(), "Lato-Regular.ttf");
                tv.setTypeface(titleFont);
            }
        }
    }*/

    protected void setActivityTitle(String title) {
        setTitle(title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }


}
