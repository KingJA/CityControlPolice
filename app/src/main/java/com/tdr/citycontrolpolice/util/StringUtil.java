package com.tdr.citycontrolpolice.util;

import java.util.UUID;

/**
 * Description：TODO
 * Create Time：2016/8/22 14:33
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StringUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
    /**
     * 身份证号码，隐藏中间的出身年月日
     */
    public static final String hideID(String id) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < id.length()-4; i++) {
            sb.append("*");
        }
        String newId = id.substring(0, 2) + sb.toString()
                + id.substring(id.length()-2);
        return newId;
    }
}
