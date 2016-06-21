package com.tdr.citycontrolpolice.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.tdr.citycontrolpolice.base.App;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/3 9:31
 * 修改备注：
 */
public class NetUtil {
    public static final int NETTYPE_WIFI = 1;
    public static final int NETTYPE_CMWAP = 2;
    public static final int NETTYPE_CMNET = 3;

    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    public static String getNetworkStringType() {
        String result = "";
        switch (getNetworkType()) {
            case NETTYPE_WIFI:
                result = "WIFI";
                break;
            case NETTYPE_CMWAP:
                result = "Wap网络";
                break;
            case NETTYPE_CMNET:
                result = "Net网络";
                break;
        }
        return result;
    }

    public static boolean netAvailable() {
        boolean netAvailable;
        ConnectivityManager connectMgr = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            netAvailable = false;
        } else {
            netAvailable = true;
        }
        return netAvailable;
    }


}
