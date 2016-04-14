package com.tdr.citycontrolpolice.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/1 14:56
 * 修改备注：
 */
public class AppInfoUtil {
    private static Context context = AppUtil.getContext();

    private static PackageInfo getAppInfo() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static String getPackageName() {
        return context.getPackageName();
    }

    public static String getVersionName() {
        return getAppInfo().versionName;
    }

    public static int getVersionCode() {
        return getAppInfo().versionCode;
    }

}
