package com.tdr.citycontrolpolice.util;

import android.util.Log;


/**
 * Created by Administrator on 2016/3/21.
 */
public class Equipment {
    public static String decode(String base) {
        byte[] b = TendencyEncrypt.decode(base.getBytes());
        if (b.length != 8) {
            return "";
        }
        byte[] c = new byte[6];
        for (int i = 0; i < c.length; i++) {
            c[i] = b[i];
        }
        String key = TendencyEncrypt.encode(c);
        short sh = CRC16M.CalculateCrc16(key.getBytes());
        byte d = (byte) (0xff & sh);
        byte g = (byte) (0xff & sh >> 8);
        if (d == b[6] && g == b[7]) {
            byte[] h = TendencyEncrypt.decode(key.getBytes());
            key = TendencyEncrypt.bytesToHexString(h);
            Log.e("good", key);
            return key.toUpperCase();
        } else {
            return "";
        }
    }
}
