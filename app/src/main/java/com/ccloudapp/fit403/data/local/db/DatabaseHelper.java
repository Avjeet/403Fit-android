package com.ccloudapp.fit403.data.local.db;

import android.util.Log;

import com.ccloudapp.fit403.data.model.DaoMaster;
import com.ccloudapp.fit403.data.model.DaoSession;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by amit on 6/2/17.
 */
@Singleton
public class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";
    private final DaoSession mDaoSession;
    private final DaoMaster mDaoMaster;
    private final DatabaseOpenHelper mDatabaseOpenHelper;


    @Inject
    public DatabaseHelper(DatabaseOpenHelper databaseOpenHelper) {
        this.mDatabaseOpenHelper = databaseOpenHelper;
        mDaoMaster = new DaoMaster(mDatabaseOpenHelper.getEncryptedWritableDb("fit-dbs"));
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    public void clearTables() {
        mDaoMaster.dropAllTables(mDatabaseOpenHelper.getWritableDb(), true);
        mDaoMaster.createAllTables(mDatabaseOpenHelper.getWritableDb(), true);
    }

}

