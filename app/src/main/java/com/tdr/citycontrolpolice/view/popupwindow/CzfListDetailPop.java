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
public class CzfListDetailPop extends PopupWindowBaseDown implements View.OnClickListener {


    private LinearLayout ll_info;

    public CzfListDetailPop(View parentView, Activity activity) {
        super(parentView, activity);
    }


    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_czf_detail, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        ll_info = (LinearLayout) popupView.findViewById(R.id.ll_info);
        ll_info.setOnClickListener(this);
    }

    @Override
    public void OnChildClick(View v) {
        if (onPopClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_info:
                onPopClickListener.onCzfInfoPop(0);
                dismiss();
                break;
            default:
                break;
        }

    }

    public interface OnPopClickListener {
        void onCzfInfoPop(int position);
    }

    private OnPopClickListener onPopClickListener;

    public void setOnPopClickListener(OnPopClickListener onPopClickListener) {
        this.onPopClickListener = onPopClickListener;
    }

}
