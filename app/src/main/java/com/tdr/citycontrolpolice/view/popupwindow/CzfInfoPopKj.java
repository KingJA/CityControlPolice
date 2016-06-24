package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 09:32
 * 修改备注：
 */
public class CzfInfoPopKj extends PopupWindowBaseDown implements View.OnClickListener {


    private static final String TAG = "CzfInfoPopKj";
    private LinearLayout ll_edit;
    private LinearLayout ll_apply;
    private LinearLayout ll_card;
    private LinearLayout ll_register;
    private LinearLayout ll_binding;
    private LinearLayout ll_manager;
    private LinearLayout ll_attention;
    private TextView tv_attention;

    public CzfInfoPopKj(View parentView, Activity activity) {
        super(parentView, activity);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void setAppleVisibility(int hasRegistered) {
        ll_apply.setVisibility(hasRegistered == 1 ? View.GONE : View.VISIBLE);
    }

    public void setAttention(int hasAttention) {
        tv_attention.setText(hasAttention==1?"取消关注":"关注出租屋");
    }

    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_czf_info, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        tv_attention = (TextView) popupView.findViewById(R.id.tv_attention);
        ll_edit = (LinearLayout) popupView.findViewById(R.id.ll_edit);
        ll_apply = (LinearLayout) popupView.findViewById(R.id.ll_apply);
        ll_card = (LinearLayout) popupView.findViewById(R.id.ll_card);
        ll_register = (LinearLayout) popupView.findViewById(R.id.ll_register);
        ll_binding = (LinearLayout) popupView.findViewById(R.id.ll_binding);
        ll_manager = (LinearLayout) popupView.findViewById(R.id.ll_manager);
        ll_attention = (LinearLayout) popupView.findViewById(R.id.ll_attention);
        ll_edit.setOnClickListener(this);
        ll_apply.setOnClickListener(this);
        ll_card.setOnClickListener(this);
        ll_register.setOnClickListener(this);
        ll_binding.setOnClickListener(this);
        ll_manager.setOnClickListener(this);
        ll_attention.setOnClickListener(this);

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
            case R.id.ll_card:
                onCzfInfoPopClickListener.onCzfInfoPop(2);
                break;
            case R.id.ll_register:
                onCzfInfoPopClickListener.onCzfInfoPop(3);
                break;
            case R.id.ll_binding:
                onCzfInfoPopClickListener.onCzfInfoPop(4);
                break;
            case R.id.ll_manager:
                onCzfInfoPopClickListener.onCzfInfoPop(5);
                break;
            case R.id.ll_attention:
                onCzfInfoPopClickListener.onCzfInfoPop(6);
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
