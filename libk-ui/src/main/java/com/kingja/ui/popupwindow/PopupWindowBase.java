package com.kingja.ui.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import java.util.List;


public abstract class PopupWindowBase<T> extends PopupWindow implements OnDismissListener,
        OnClickListener {

    protected Activity activity;
    private List<T> data;
    protected View popupView;

    public PopupWindowBase(Activity activity, List<T> data) {
        this.activity = activity;
        this.data = data;
        popupView = setPopupView(activity);
        // 查找点击事件的控件
        initChildView(data);
        // 设置PopupWindow布局
        this.setContentView(popupView);
        // 设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 点击PopupWindow以外区域隐藏
        this.setFocusable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        // 监听PopupWindow关闭
        this.setOnDismissListener(this);
        // 可以PopupWindow点击
        this.setFocusable(true);
        // 设置PopupWindow出现和消失动画
//        this.setAnimationStyle(R.style.PopupAnimation);
    }


    /**
     * @return
     */
    public abstract View setPopupView(Activity activity);

    public abstract void initChildView(List<T> data);

    public void showPopAtLocation(View parent, int gravity, int x, int y) {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAtLocation(parent, gravity, x, y);

        }

    }

    public void showPopAsDropDown(View anchor) {
        showAsDropDownAnimate(anchor, 0, 0);
    }

    public void showPopAtBottom(View anchor) {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAtLocation(anchor,  Gravity.BOTTOM, 0, 0);

        }
    }

    public void showPopAsDropDown(View anchor, int xoff, int yoff) {
        showAsDropDownAnimate(anchor, xoff, yoff);
    }

    private void showAsDropDownAnimate(View anchor, int xoff, int yoff) {
        if (!this.isShowing()) {
            setAlpha(activity, 0.7f);
            this.showAsDropDown(anchor, xoff, yoff);
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
        lp.alpha = bgAlpha; // 0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setAlpha(activity, 1f);
    }

    @Override
    public void onDismiss() {
        setAlpha(activity, 1f);
    }

    @Override
    public void onClick(View v) {
        OnChildClick(v);
    }

    /**
     * @param v
     */
    public abstract void OnChildClick(View v);



}