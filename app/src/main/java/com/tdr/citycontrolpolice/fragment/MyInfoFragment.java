package com.tdr.citycontrolpolice.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.base.BaseFragment;
import com.tdr.citycontrolpolice.util.ToastUtil;

/**
 * Created by Administrator on 2016/3/29.
 */
public class MyInfoFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_about, ll_modify, ll_logout, ll_personal_question, ll_personal_fankui;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_personal, null);
        init_view(view);
        return view;
    }

    /**
     * 初始化控件
     */
    private void init_view(View v) {


        //修改密码
        ll_modify = (LinearLayout) v.findViewById(R.id.ll_personal_modify);
        ll_modify.setOnClickListener(this);
        //关于我们
        ll_personal_question = (LinearLayout) v.findViewById(R.id.ll_personal_question);
        ll_personal_question.setOnClickListener(this);

        ll_personal_fankui = (LinearLayout) v.findViewById(R.id.ll_personal_fankui);
        ll_personal_fankui.setOnClickListener(this);

        ll_about = (LinearLayout) v.findViewById(R.id.ll_personal_about);
        ll_about.setOnClickListener(this);

        //退出登录
        ll_logout = (LinearLayout) v.findViewById(R.id.ll_personal_logout);
        ll_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == ll_modify) {
//            Intent intent = new Intent(mActivity, ModifyPwdActivity.class);
//            startActivity(intent);
            ToastUtil.showMyToast("正在开发中...");

        }

        if (v == ll_personal_question) {
            ToastUtil.showMyToast("正在开发中...");
        }
        if (v == ll_personal_fankui) {

            ToastUtil.showMyToast("正在开发中...");
        }
        if (v == ll_about) {
//            Intent intent = new Intent(mActivity, AboutActivity.class);
//            startActivity(intent);
            ToastUtil.showMyToast("正在开发中...");
        }
        if (v == ll_logout) {
            ToastUtil.showMyToast("正在开发中...");
//            AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
//
//            dialog.setTitle("提示");
//            dialog.setMessage("您是否要退出当前账号……");
//            dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if (UserService.getInstance(mActivity).logout()) {
//                        Intent intent = new Intent(mActivity, LoginActivity.class);
//                        startActivity(intent);
//                        mActivity.finish();
//                    }
//                }
//            });
//            dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.create().show();
        }
    }
}
