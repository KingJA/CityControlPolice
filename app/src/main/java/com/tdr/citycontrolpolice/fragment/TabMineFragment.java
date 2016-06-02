package com.tdr.citycontrolpolice.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.BluetoothChangeActivity;
import com.tdr.citycontrolpolice.activity.MineAboutActivity;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：我的信息Fragment
 * 创建人：KingJA
 * 创建时间：2016/4/26 9:58
 * 修改备注：
 */
public class TabMineFragment extends Fragment implements View.OnClickListener, DialogDouble.OnDoubleClickListener {

    private LinearLayout ll_personal_about;
    private LinearLayout ll_personal_modify;
    private LinearLayout ll_personal_question;
    private LinearLayout ll_personal_report;
    private LinearLayout ll_personal_quit;
    private View view;
    private LinearLayout ll_personal_change;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        initView();
        return view;
    }


    private void initView() {
        ll_personal_modify = (LinearLayout) view.findViewById(R.id.ll_personal_modify);
        ll_personal_question = (LinearLayout) view.findViewById(R.id.ll_personal_question);
        ll_personal_report = (LinearLayout) view.findViewById(R.id.ll_personal_report);
        ll_personal_about = (LinearLayout) view.findViewById(R.id.ll_personal_about);
        ll_personal_quit = (LinearLayout) view.findViewById(R.id.ll_personal_quit);
        ll_personal_change = (LinearLayout) view.findViewById(R.id.ll_personal_change);
        ll_personal_modify.setOnClickListener(this);
        ll_personal_question.setOnClickListener(this);
        ll_personal_report.setOnClickListener(this);
        ll_personal_about.setOnClickListener(this);
        ll_personal_quit.setOnClickListener(this);
        ll_personal_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_personal_modify:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            case R.id.ll_personal_question:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            case R.id.ll_personal_report:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            case R.id.ll_personal_about:
                ActivityUtil.goActivity(getActivity(), MineAboutActivity.class);
                break;
            case R.id.ll_personal_quit:
                DialogDouble dialogDouble = new DialogDouble(getActivity(), "您确定要退出应用？", "确定", "取消");
                dialogDouble.show();
                dialogDouble.setOnDoubleClickListener(this);
                break;
            case R.id.ll_personal_change:
                ActivityUtil.goActivity(getActivity(), BluetoothChangeActivity.class);
                break;
            default:
                break;

        }
    }

    @Override
    public void onLeft() {
        System.exit(0);
    }

    @Override
    public void onRight() {

    }
}
