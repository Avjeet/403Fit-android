package com.ccloudapp.fit403.ui.home;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.DataManager;
import com.ccloudapp.fit403.ui.base.BaseActivity;
import com.ccloudapp.fit403.ui.menu_activity.TrackWorkoutActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NavigationHomeActivity extends BaseActivity implements
        NavigationDrawerAdapter.OnNavDrawerItemClickListener {

    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    NavigationDrawerAdapter mNavigationDrawerAdapter;

    private Handler mHandler;

    protected static final int NAVDRAWER_ITEM_BROSWE_PEOPLE = 0;

    protected static final int NAVDRAWER_ITEM_MY_FRIENDS = 1;

    protected static final int NAVDRAWER_ITEM_CHAT_MESSAGES = 2;

    protected static final int NAVDRAWER_ITEM_GROUPS = 3;

    protected static final int NAVDRAWER_ITEM_TRACK_WORKOUT = 4;

    protected static final int NAVDRAWER_ITEM_STEP_COUNTER = 5;

    protected static final int NAVDRAWER_ITEM_SETTINGS = 6;

    protected static final int NAVDRAWER_ITEM_HELP = 7;

    protected static final int NAVDRAWER_ITEM_BUG_REPORTING = 8;

    protected static final int NAVDRAWER_ITEM_ABOUT = 9;

    protected static final int NAVDRAWER_ITEM_TERMS_AND_CONDITION = 10;

    protected static final int NAVDRAWER_ITEM_INVALID = -1;

    protected static final int NAVDRAWER_ITEM_SEPARATOR = -2;

    protected static final int NAVDRAWER_ITEM_HEADER = -3;

    private static final int[] NAVDRAWER_ITEMS =
            {R.string.browse_people, R.string.my_friends, R.string.chat_and_messages,
                    R.string.groups, R.string.track_workout, R.string.step_counter,
                    R.string.settings, R.string.help, R.string.bug, R.string.terms};
    private static final int[] NAVDRAWER_ICONS =
            {R.drawable.ic_people_outline_grey_24dp, R.drawable.ic_people_grey_24dp,
                    R.drawable.ic_chat_grey_24dp,
                    R.drawable.ic_people_outline_grey_24dp,
                    R.drawable.ic_dumbbell_variant_outline,
                    R.drawable.ic_stick_man_running_on_a_treadmill,
                    0, 0, 0, 0};

    @Inject
    DataManager mDataManager;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;

    private static final int MAIN_CONTENT_FADEIN_DURATION = 250;

    private ArrayList<Integer> mNavDrawerItems = new ArrayList<>();

    private static final String TAG = "NavigationHomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        mHandler = new Handler();
    }

    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_INVALID;
    }

    private void setupNavDrawer() {
        int selfItem = getSelfNavDrawerItem();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.navigationDrawerRecyclerView);

        if (selfItem == NAVDRAWER_ITEM_INVALID) {
            // do not show a nav drawer
            if (mRecyclerView != null) {
                ((ViewGroup) mRecyclerView.getParent()).removeView(mRecyclerView);
            }
            mDrawerLayout = null;
            return;
        }

        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(
                    v -> mDrawerLayout.openDrawer(GravityCompat.START));
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        populateNavigationDrawer();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getToolbar();
    }

    protected Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        return mToolbar;
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void populateNavigationDrawer() {
        mNavDrawerItems.clear();
        mNavDrawerItems.add(NAVDRAWER_ITEM_SEPARATOR);
        mNavDrawerItems.add(NAVDRAWER_ITEM_BROSWE_PEOPLE);
        mNavDrawerItems.add(NAVDRAWER_ITEM_MY_FRIENDS);
        mNavDrawerItems.add(NAVDRAWER_ITEM_CHAT_MESSAGES);
        mNavDrawerItems.add(NAVDRAWER_ITEM_GROUPS);
        mNavDrawerItems.add(NAVDRAWER_ITEM_TRACK_WORKOUT);
        mNavDrawerItems.add(NAVDRAWER_ITEM_STEP_COUNTER);
        mNavDrawerItems.add(NAVDRAWER_ITEM_SEPARATOR);
        mNavDrawerItems.add(NAVDRAWER_ITEM_SETTINGS);
        mNavDrawerItems.add(NAVDRAWER_ITEM_HELP);
        mNavDrawerItems.add(NAVDRAWER_ITEM_BUG_REPORTING);
        mNavDrawerItems.add(NAVDRAWER_ITEM_TERMS_AND_CONDITION);

        createNavDrawerItems();

    }

    private void createNavDrawerItems() {
        List<NavDrawerItem> list = new ArrayList<>();
        mNavigationDrawerAdapter = new NavigationDrawerAdapter(this, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mNavigationDrawerAdapter);

        list.add(headerItem());

        int j = 0;
        for (int i = 0; i < mNavDrawerItems.size(); i++) {

            if (isSeparator(mNavDrawerItems.get(i))) {
                NavDrawerItem navDrawerItem = new NavDrawerItem();
                navDrawerItem.itemId = mNavDrawerItems.get(i);
                list.add(navDrawerItem);
            } else {
                NavDrawerItem navDrawerItem = new NavDrawerItem();
                navDrawerItem.title = getResources().getString(NAVDRAWER_ITEMS[j]);
                navDrawerItem.drawableId = NAVDRAWER_ICONS[j];
                navDrawerItem.itemId = mNavDrawerItems.get(i);
                list.add(navDrawerItem);
                ++j;
            }
        }
        mNavigationDrawerAdapter.updateList(list);
        Log.i(TAG, "createNavDrawerItems:" + getSelfNavDrawerItem());
        mNavigationDrawerAdapter.setSelected(getSelfNavDrawerItem());

    }

    public boolean isSeparator(int itemId) {
        return itemId == NAVDRAWER_ITEM_SEPARATOR;
    }

    private NavDrawerItem headerItem() {
        NavDrawerItem navDrawerItem = new NavDrawerItem();
        navDrawerItem.itemId = NAVDRAWER_ITEM_HEADER;
        navDrawerItem.title = mDataManager.getPreferenceHelper().getName();
        navDrawerItem.email = mDataManager.getPreferenceHelper().getEmail();
        return navDrawerItem;
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        } else {
            Log.w(TAG, "No view with ID main_content to fade in.");
        }
    }

    @Override
    public void onClick(NavDrawerItem navDrawerItem) {
        if (navDrawerItem.itemId == getSelfNavDrawerItem()) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        mHandler.postDelayed(() -> goToNavDrawerItem(navDrawerItem.itemId), NAVDRAWER_LAUNCH_DELAY);
        mNavigationDrawerAdapter.setSelected(navDrawerItem.itemId);
        // fade out the main content
        View mainContent = findViewById(R.id.main_content);
        if (mainContent != null) {
            mainContent.animate().alpha(0).setDuration(MAIN_CONTENT_FADEOUT_DURATION);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

    }

    private void goToNavDrawerItem(int position) {
        switch (position) {
            case 4:
                Intent trackWorkoutAcitvity = new Intent(this, TrackWorkoutActivity.class);
                createBackStack(trackWorkoutAcitvity);
            default:
                Toast.makeText(this, "Clicked " + position, Toast.LENGTH_SHORT).show();
        }
    }

    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }
}
