package com.tdr.citycontrolpolice.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.view.loadpager.LoadingAndRetryManager;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
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
        initLoadPager();
        initXutils3();
    }

    /**
     * 初始化xutils3，用于数据库操作
     */
    private void initXutils3() {
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    /**
     * 初始化加载布局
     */
    private void initLoadPager() {
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
    }

    public static Context getContext() {
        return mAppContext;
    }

    public static SharedPreferences getSP() {
        return mSharedPreferences;
    }
}
