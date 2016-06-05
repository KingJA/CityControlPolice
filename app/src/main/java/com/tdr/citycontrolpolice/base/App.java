package com.tdr.citycontrolpolice.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.leakcanary.LeakCanary;
import com.tdr.citycontrolpolice.util.CustomConstants;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：应用Application
 * 创建人：KingJA
 * 创建时间：2016/3/25 16:31
 * 修改备注：
 */
public class App extends Application {

    private static Context mAppContext;
    private static SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        mSharedPreferences = getSharedPreferences(CustomConstants.APPLICATION_NAME,
                MODE_PRIVATE);
//        CrashReport.initCrashReport(mAppContext, "900026215", false);
        LeakCanary.install(this);
        initXutils3();
    }

    /**
     * 初始化xutils3，用于数据库操作
     */
    private void initXutils3() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    public static Context getContext() {
        return mAppContext;
    }

    public static SharedPreferences getSP() {
        return mSharedPreferences;
    }


}
