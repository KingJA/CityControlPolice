package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.enums.CzfOperation;

import java.util.Arrays;

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
    private LinearLayout llv_admin;
    private TextView tv_attention;
    private LinearLayout ll_changeCode;
    private LinearLayout ll_bind_access;

    public CzfInfoPopKj(View parentView, Activity activity) {
        super(parentView, activity);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void setAppleVisibility(int hasRegistered) {
        ll_apply.setVisibility(hasRegistered == 1 ? View.GONE : View.VISIBLE);
    }

    public void setAttention(int hasAttention) {
        tv_attention.setText(hasAttention == 1 ? "取消关注" : "关注出租屋");
    }

    public void setAccess(String result, String contain) {
        String[] deviceArr = result.split(",");
        ll_bind_access.setVisibility(Arrays.asList(deviceArr).contains(contain) ? View.GONE : View.VISIBLE);
    }

    public void setAccess(int visibility) {
        ll_bind_access.setVisibility(visibility);
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
        ll_changeCode = (LinearLayout) popupView.findViewById(R.id.ll_changeCode);
        ll_bind_access = (LinearLayout) popupView.findViewById(R.id.ll_bind_access);
        ll_attention = (LinearLayout) popupView.findViewById(R.id.ll_attention);
        llv_admin = (LinearLayout) popupView.findViewById(R.id.llv_admin);
        ll_edit.setOnClickListener(this);
        ll_apply.setOnClickListener(this);
        ll_card.setOnClickListener(this);
        ll_register.setOnClickListener(this);
        ll_binding.setOnClickListener(this);
        ll_manager.setOnClickListener(this);
        ll_changeCode.setOnClickListener(this);
        ll_bind_access.setOnClickListener(this);
        ll_attention.setOnClickListener(this);
        llv_admin.setOnClickListener(this);

    }

    @Override
    public void OnChildClick(View v) {
        if (onCzfInfoPopClickListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_edit:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.HouseEdit);
                break;
            case R.id.ll_apply:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.DeviceApply);
                break;
            case R.id.ll_card:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.CardRecord);
                break;
            case R.id.ll_register:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.OutInRecord);
                break;
            case R.id.ll_binding:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.FangkongBind);
                break;
            case R.id.ll_manager:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.DeviceManager);
                break;
            case R.id.ll_changeCode:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.CodeChange);
                break;
            case R.id.ll_bind_access:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.MenjinBind);
                break;
            case R.id.ll_attention:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.Attention);
                break;
            case R.id.llv_admin:
                onCzfInfoPopClickListener.onCzfInfoPop(CzfOperation.Admins);
                break;
            default:
                break;
        }

    }

    public interface OnCzfInfoPopClickListener {
        void onCzfInfoPop(CzfOperation operation);
    }

    private OnCzfInfoPopClickListener onCzfInfoPopClickListener;

    public void setOnCzfInfoPopClickListener(OnCzfInfoPopClickListener onCzfInfoPopClickListener) {
        this.onCzfInfoPopClickListener = onCzfInfoPopClickListener;
    }



}
