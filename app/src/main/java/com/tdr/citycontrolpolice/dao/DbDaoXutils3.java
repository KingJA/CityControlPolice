package com.tdr.citycontrolpolice.dao;


import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 13:57
 * 修改备注：
 */
public class DbDaoXutils3<T> implements DbDao<T> {

    private final DbManager dbManager;
    private static DbDaoXutils3 mDbDaoXutils3;

    public static DbDaoXutils3 getInstance() {
        if (mDbDaoXutils3 == null) {
            synchronized (DbDaoXutils3.class) {
                if (mDbDaoXutils3 == null) {
                    mDbDaoXutils3 = new DbDaoXutils3();
                    return mDbDaoXutils3;
                }
            }
        }
        return mDbDaoXutils3;
    }

    private DbDaoXutils3() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("citypolice.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                });

        dbManager = x.getDb(daoConfig);
    }

    @Override
    public T sleectFirst(Class<T> clazz, String key, String value) {
        T t = null;
        try {
            t = dbManager.selector(clazz).where(key, "=", value).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public T sleectFirst(Class<T> clazz, String key1, String value1, String key2, String value2) {
        T t = null;
        try {
            t = dbManager.selector(clazz).where(key1, "=", value1).and(key2, "=", value2).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public List<T> sleectAll(Class<T> clazz, String key, String value) {
        List<T> list = null;
        try {
            list = dbManager.selector(clazz).where(key, "=", value).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return list;
    }
}
