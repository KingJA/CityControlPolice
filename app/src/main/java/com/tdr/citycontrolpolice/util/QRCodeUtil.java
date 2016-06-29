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
        Log.i(TAG, "result: " + result);
        String typeStirng = result.substring(result.indexOf("?") + 1);
        String type = typeStirng.substring(0, 2);
        if (type.equals("AD")) {
            String base = typeStirng.substring(2);
            byte[] s = TendencyEncrypt.decode(base.getBytes());
            String code = TendencyEncrypt.bytesToHexString(s);
            String newcode = code.substring(0, 6) + code.substring(9);
            int i = newcode.length();
            newcode = newcode.substring(0, i - 4);
            Log.e("newcode", newcode);
            return newcode;
        }
         if (result.startsWith("http://xinjumin.ouhai.gov.cn:8060/zzsb")) {
            int length = result.length();
            result = result.substring(length - 13);
            StringBuilder sb = new StringBuilder(result);
             String newcode = sb.insert(6, 90).toString();
            if (!newcode.startsWith("3303")) {
                ToastUtil.showMyToast("非指定设备");
                return "";
            }
            return newcode;
        }
        ToastUtil.showMyToast("非指定设备");
        return "";
    }
}
