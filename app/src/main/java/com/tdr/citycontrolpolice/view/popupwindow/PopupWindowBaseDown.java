package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 09:02
 * 修改备注：
 */
public abstract class PopupWindowBaseDown<T> extends PopupWindow implements OnDismissListener,
        OnClickListener {

    private static final String TAG = "PopupWindowBaseDown";
    protected Activity activity;
    protected View popupView;
    protected PopupWindowBaseDown sharePopupWindow;
    protected View parentView;
    protected T data;

    public PopupWindowBaseDown(View parentView, Activity activity, T data) {
        this.activity = activity;
        this.parentView = parentView;
        this.data = data;
        popupView = setPopupView(activity);
        initChildView();
        this.setContentView(popupView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOnDismissListener(this);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupTopAnimation);
    }

    public PopupWindowBaseDown(View parentView, Activity activity) {
        this.activity = activity;
        this.parentView = parentView;
        popupView = setPopupView(activity);
        initChildView();
        this.setContentView(popupView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOnDismissListener(this);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupTopAnimation);
    }


    public abstract View setPopupView(Activity activity);


    public void showPopupWindow() {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        }

    }

    public void showPopupWindowDown() {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAsDropDown(parentView, (int) (-parentView.getWidth() * 1.3f), 0);
        }
    }

    public void closePopupWindow(Activity activity) {
        if (this.isShowing()) {
            this.dismiss();
            setAlpha(activity, 1f);
        }

    }

    private void setAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setAlpha(activity, 1f);

    }

    @Override
    public void onClick(View v) {
        OnChildClick(v);
    }

    public abstract void initChildView();

    public abstract void OnChildClick(View v);


}