package com.tdr.citycontrolpolice.util;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/9 10:20
 * 修改备注：
 */
public class ViewUtil {

    private static final String TAG = "ViewUtil";
    private static int viewWidth;
    private static int viewHeight;

    public static int getViewHeight(final View view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                viewHeight = view.getHeight();
            }
        });
        return viewHeight;
    }

    public static int getViewWidth(final View view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                viewWidth = view.getWidth();
                Log.i(TAG, "viewWidth: " + viewWidth);
            }
        });
        return viewWidth;
    }
}
