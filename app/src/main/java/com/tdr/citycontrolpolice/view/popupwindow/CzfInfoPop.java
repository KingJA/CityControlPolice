package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 09:32
 * 修改备注：
 */
public class CzfInfoPop extends PopupWindowBaseDown implements View.OnClickListener {


    private LinearLayout ll_edit;
    private LinearLayout ll_apply;
    private LinearLayout ll_register;

    public CzfInfoPop(View parentView, Activity activity) {
        super(parentView, activity);
    }


    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_czf_info, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        ll_edit = (LinearLayout) popupView.findViewById(R.id.ll_edit);
        ll_apply = (LinearLayout) popupView.findViewById(R.id.ll_apply);
        ll_register = (LinearLayout) popupView.findViewById(R.id.ll_register);
        ll_edit.setOnClickListener(this);
        ll_apply.setOnClickListener(this);
        ll_register.setOnClickListener(this);
    }

    @Override
    public void OnChildClick(View v) {
        if (onCzfInfoPopClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_edit:
                onCzfInfoPopClickListener.onCzfInfoPop(0);
                break;
            case R.id.ll_apply:
                onCzfInfoPopClickListener.onCzfInfoPop(1);
                break;
            case R.id.ll_register:
                onCzfInfoPopClickListener.onCzfInfoPop(2);
                break;
            default:
                break;
        }

    }

    public interface OnCzfInfoPopClickListener {
        void onCzfInfoPop(int position);
    }

    private OnCzfInfoPopClickListener onCzfInfoPopClickListener;

    public void setOnCzfInfoPopClickListener(OnCzfInfoPopClickListener onCzfInfoPopClickListener) {
        this.onCzfInfoPopClickListener = onCzfInfoPopClickListener;
    }

}
