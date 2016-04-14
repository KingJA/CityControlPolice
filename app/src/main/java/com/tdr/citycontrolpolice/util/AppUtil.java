package com.tdr.citycontrolpolice.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.tdr.citycontrolpolice.base.App;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/28 11:13
 * 修改备注：
 */
public class AppUtil {
    public static Context getContext() {
        return App.getContext();
    }

    public static int dip2px(int dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    public static int px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    //    public static int getScreenWidth() {
//        WindowManager manager = (WindowManager) getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        return display.getWidth();
//    }
//
//    public static int getScreenHeight() {
//        WindowManager manager = (WindowManager) getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        return display.getHeight();
//    }
    public static int getScreenWidth() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }
}
