package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/6 17:13
 * 修改备注：
 */
public class PopupDevice extends PopupWindowBaseDown<String> {

    private ImageView iv_close;
    private ImageView iv_unbind;
    private ImageView iv_change;
    private OnPopupDeviceListener onPopupDeviceListener;

    public PopupDevice(View parentView, Activity activity) {
        super(parentView, activity);
        this.setAnimationStyle(R.style.PopupScaleAnimation);
    }

    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.popup_device, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        iv_close = (ImageView) popupView.findViewById(R.id.iv_close);
        iv_unbind = (ImageView) popupView.findViewById(R.id.iv_unbind);
        iv_change = (ImageView) popupView.findViewById(R.id.iv_change);
        iv_close.setOnClickListener(this);
        iv_unbind.setOnClickListener(this);
        iv_change.setOnClickListener(this);
    }

    @Override
    public void OnChildClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.iv_close:
                break;
            case R.id.iv_unbind:
                if (onPopupDeviceListener != null) {
                    onPopupDeviceListener.onUnbind();
                }
                break;
            case R.id.iv_change:
                if (onPopupDeviceListener != null) {
                    onPopupDeviceListener.onChange();
                }
                break;
        }
    }

    public interface OnPopupDeviceListener {
        void onChange();

        void onUnbind();
    }

    public void setOnPopupDeviceListener(OnPopupDeviceListener onPopupDeviceListener) {
        this.onPopupDeviceListener = onPopupDeviceListener;
    }
}
