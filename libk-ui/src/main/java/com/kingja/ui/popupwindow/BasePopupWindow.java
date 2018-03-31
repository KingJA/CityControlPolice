package com.kingja.ui.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.kingja.ui.R;


/**
 * Description：TODO
 * Create Time：2016/9/1 14:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BasePopupWindow<T> extends PopupWindow implements OnDismissListener,
        OnClickListener {

    private static final String TAG = "PopupWindowBaseDown";
    protected Activity activity;
    protected View popupView;
    protected View parentView;
    protected T data;

    public BasePopupWindow(View parentView, Activity activity, T data) {
        this.activity = activity;
        this.parentView = parentView;
        this.data = data;
        popupView = setPopupView(activity);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        initChildView();
        this.setContentView(popupView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOnDismissListener(this);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupTopRightAnimation);
    }

    public BasePopupWindow(View parentView, Activity activity) {
        this.activity = activity;
        this.parentView = parentView;
        popupView = setPopupView(activity);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        initChildView();
        this.setContentView(popupView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOnDismissListener(this);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupTopRightAnimation);
    }


    public abstract View setPopupView(Activity activity);


    public void showPopupWindow() {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

        }

    }

    public void showPopupWindowDownOffset() {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAsDropDown(parentView, (int) (-parentView.getWidth() * 1.3f), 0);
        }
    }

    public void showPopupWindowDown() {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAsDropDown(parentView);
        }
    }

    public void showPopupWindowCenter() {
        int[] location = new int[2];
        parentView.getLocationOnScreen(location);
        Log.i(TAG, "location[0]: " + location[0] + "location[1]: " + location[1]);
        if (!this.isShowing()) {
//            setAlpha(activity, 0.7f);
//            this.showAtLocation(parentView, Gravity.CENTER, 0,0);
            int popHeight = this.getContentView().getMeasuredHeight();
            int popWeight = this.getContentView().getMeasuredHeight();
            Log.i(TAG, "popHeight" + popHeight);
            Log.i(TAG, "popWeight" + popWeight);
            this.showAtLocation(parentView, Gravity.NO_GRAVITY, location[0] - popWeight + (int) (1.5f * parentView.getWidth()), location[1] - popHeight + (int) (1.5f * parentView.getHeight()));

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