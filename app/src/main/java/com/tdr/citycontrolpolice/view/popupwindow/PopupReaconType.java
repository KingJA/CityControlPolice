package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 09:32
 * 修改备注：
 */
public class PopupReaconType extends PopupWindowBaseDown implements View.OnClickListener {


    private static final String TAG = "PopupReaconType";
    private TextView mTvBroken;
    private TextView mTvLose;
    private TextView mTvOther;


    public PopupReaconType(View parentView, Activity activity) {
        super(parentView, activity);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_reason_type, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        mTvBroken = (TextView) popupView.findViewById(R.id.tv_broken);
        mTvLose = (TextView) popupView.findViewById(R.id.tv_lose);
        mTvOther = (TextView) popupView.findViewById(R.id.tv_other);
        mTvBroken.setOnClickListener(this);
        mTvLose.setOnClickListener(this);
        mTvOther.setOnClickListener(this);
    }

    @Override
    public void OnChildClick(View v) {
        if (onReasonTypeSelectListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_broken:
                onReasonTypeSelectListener.onReasonTypeSelect(1);
                break;
            case R.id.tv_lose:
                onReasonTypeSelectListener.onReasonTypeSelect(2);
                break;
            case R.id.tv_other:
                onReasonTypeSelectListener.onReasonTypeSelect(9);
                break;
        }
        dismiss();

    }

    public interface OnReasonTypeSelectListener {
        void onReasonTypeSelect(int position);
    }

    private OnReasonTypeSelectListener onReasonTypeSelectListener;

    public void setOnReasonTypeSelectListener(OnReasonTypeSelectListener onReasonTypeSelectListener) {
        this.onReasonTypeSelectListener = onReasonTypeSelectListener;
    }

}
