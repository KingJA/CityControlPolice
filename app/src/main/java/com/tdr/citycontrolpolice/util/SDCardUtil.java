package com.tdr.citycontrolpolice.util;

import android.os.Environment;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：SD卡管理类
 * 创建人：KingJA
 * 创建时间：2016/5/13 8:50
 * 修改备注：
 */
public class SDCardUtil {
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
