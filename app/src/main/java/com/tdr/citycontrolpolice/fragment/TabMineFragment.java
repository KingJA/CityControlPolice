package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.CommonQuestionActivity;
import com.tdr.citycontrolpolice.activity.MineAboutActivity;
import com.tdr.citycontrolpolice.util.GoUtil;
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
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        initView();
        return view;
    }


    private void initView() {
        LinearLayout ll_personal_modify = (LinearLayout) view.findViewById(R.id.ll_personal_modify);
        LinearLayout ll_personal_question = (LinearLayout) view.findViewById(R.id.ll_personal_question);
        LinearLayout ll_personal_report = (LinearLayout) view.findViewById(R.id.ll_personal_report);
        LinearLayout ll_personal_about = (LinearLayout) view.findViewById(R.id.ll_personal_about);
        LinearLayout ll_personal_quit = (LinearLayout) view.findViewById(R.id.ll_personal_quit);
        ll_personal_modify.setOnClickListener(this);
        ll_personal_question.setOnClickListener(this);
        ll_personal_report.setOnClickListener(this);
        ll_personal_about.setOnClickListener(this);
        ll_personal_quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_personal_modify:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            case R.id.ll_personal_question:
                GoUtil.goActivity(getActivity(), CommonQuestionActivity.class);
                break;
            case R.id.ll_personal_report:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            case R.id.ll_personal_about:
                GoUtil.goActivity(getActivity(), MineAboutActivity.class);
                break;
            case R.id.ll_personal_quit:
                DialogDouble dialogDouble = new DialogDouble(getActivity(), "您确定要退出应用？", "确定", "取消");
                dialogDouble.show();
                dialogDouble.setOnDoubleClickListener(this);
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
