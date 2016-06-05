package com.tdr.citycontrolpolice.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/5 17:11
 * 修改备注：
 */
public class QRCodeUtil {
    private static final String TAG = "QRCodeUtil";

    public static String  inquireCzf(Intent data) {
        Bundle bundle = data.getExtras();
        String result = bundle.getString("result");
        Log.i(TAG, "Camera result: " + result);
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if (type.equals("AD")) {
            String base = result.substring(2);
            byte[] s = TendencyEncrypt.decode(base.getBytes());
            String code = TendencyEncrypt.bytesToHexString(s);
            Log.i(TAG, "TendencyEncrypt code: " + code);
            String newcode = code.substring(0, 6) + code.substring(9);
            int i = newcode.length();
            newcode = newcode.substring(0, i - 4);
            Log.e("newcode", newcode);
            return newcode;
        } else {
            ToastUtil.showMyToast("非指定设备");
            return "";
        }
    }
}
