package com.tdr.citycontrolpolice.util;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.tdr.citycontrolpolice.base.App;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/5 16:10
 * 修改备注：
 */
public class ResourcesUtil {

    @NonNull
    public static String getString(@StringRes int id) throws Resources.NotFoundException {
        String res= App.getContext().getResources().getString(id);
        if (res != null) {
            return App.getContext().getResources().getString(id);
        }
        throw new Resources.NotFoundException("String resource ID #0x"
                + Integer.toHexString(id));
    }
}
