package com.tdr.citycontrolpolice.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.tdr.citycontrolpolice.entity.Param_User_LoginByPolice;
import com.tdr.citycontrolpolice.entity.PhoneInfo;

/**
 * Created by Administrator on 2016/2/26.
 */
public class PhoneUtil {
    private Context context;

    public PhoneUtil(Context context) {
        this.context = context;
    }

    /**
     * 获取IMEI号，IESI号，手机型号
     */
    public PhoneInfo getInfo() {
        PhoneInfo phoneInfo = new PhoneInfo();
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();//imei
        String imsi = mTm.getSubscriberId();//imsi
        String iccid = mTm.getSimSerialNumber();//iccid
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb = android.os.Build.BRAND;//手机品牌
        String SYSTEMVERSION = android.os.Build.VERSION.RELEASE;//系统版本号
        String androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        UUID deviceUuid =new UUID(androidId.hashCode(), ((long)imei.hashCode() << 32) | iccid.hashCode());
//        String uniqueIuniqueId= deviceUuid.toString();//设备ID
        String btmac = BluetoothAdapter.getDefaultAdapter().getAddress();
        phoneInfo.setIMEI(imei);
        phoneInfo.setIMSI(imsi);
        phoneInfo.setBTMAC(btmac);
        phoneInfo.setDEVICEMODEL(mtype);
        phoneInfo.setICCID(iccid);
        phoneInfo.setSYSTEMVERSION(SYSTEMVERSION);
        phoneInfo.setSYSTEMTYPE("Android");
        phoneInfo.setWIFIMAC(getMacAddress());
        return phoneInfo;
    }

    public Param_User_LoginByPolice.PHONEINFOBean getPhoneInfo() {
        Param_User_LoginByPolice.PHONEINFOBean phoneInfo = new Param_User_LoginByPolice.PHONEINFOBean();
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = mTm.getDeviceId();//imei
        String imsi = mTm.getSubscriberId();//imsi
        String iccid = mTm.getSimSerialNumber();//iccid
        String mtype = android.os.Build.MODEL; // 手机型号
        String mtyb = android.os.Build.BRAND;//手机品牌
        String SYSTEMVERSION = android.os.Build.VERSION.RELEASE;//系统版本号
        String androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        UUID deviceUuid =new UUID(androidId.hashCode(), ((long)imei.hashCode() << 32) | iccid.hashCode());
//        String uniqueIuniqueId= deviceUuid.toString();//设备ID
        String btmac = BluetoothAdapter.getDefaultAdapter().getAddress();
        phoneInfo.setIMEI(imei);
        phoneInfo.setIMSI(imsi);
        phoneInfo.setBTMAC(btmac);
        phoneInfo.setDEVICEMODEL(mtype);
        phoneInfo.setICCID(iccid);
        phoneInfo.setSYSTEMVERSION(SYSTEMVERSION);
        phoneInfo.setSYSTEMTYPE("Android");
        phoneInfo.setWIFIMAC(getMacAddress());
        return phoneInfo;
    }

    /**
     * .获取手机MAC地址
     * 只有手机开启wifi才能获取到mac地址
     */
    private String getMacAddress() {
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        Log.i("text", "手机macAdd:" + result);
        return result;
    }


}
