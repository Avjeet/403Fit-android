package com.ccloudapp.fit403.data.local.db;

import android.content.Context;

import com.ccloudapp.fit403.data.model.DaoMaster;
import com.ccloudapp.fit403.di.context.ApplicationContext;
import com.ccloudapp.fit403.di.scopes.DatabaseName;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by amit on 5/2/17.
 */
@Singleton
public class DatabaseOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DatabaseOpenHelper(@ApplicationContext Context context, @DatabaseName String name) {
        super(context, "dot-database");
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

}
