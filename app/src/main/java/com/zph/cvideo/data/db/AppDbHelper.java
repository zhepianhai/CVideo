package com.zph.cvideo.data.db;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.zph.cvideo.BuildConfig;
import com.zph.cvideo.data.db.dao.DaoMaster;
import com.zph.cvideo.data.db.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author zph
 * @date 2018/4/27
 */

@Singleton
public class AppDbHelper implements DbHelper {
    private final DaoSession mDaoSession;

    @Inject
    AppDbHelper(MySQLiteOpenHelper helper) {
        //如果你想查看日志信息，请将DEBUG设置为true
        MigrationHelper.DEBUG = BuildConfig.DEBUG;
        Database db = helper.getWritableDb();
        this.mDaoSession = new DaoMaster(db).newSession();
    }

    @Override
    public void initCategory(int type, String[] value, String[] name) {
    }

}
