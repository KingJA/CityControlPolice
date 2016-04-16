package com.tdr.citycontrolpolice.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.tdr.citycontrolpolice.view.dialog.DialogProgress;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 9:56
 * 修改备注：
 */
public abstract class BaseActivity extends FragmentActivity {


    protected DialogProgress mDialogProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConmonView();
        initVariables();
        initView();
        initNet();
        initData();
        setData();
    }

    private void initConmonView() {
        mDialogProgress = new DialogProgress(this);
    }

    public void setProgressDialog(boolean show) {
        if (show) {
            mDialogProgress.show();
        } else {
            mDialogProgress.dismiss();
        }
    }




    public abstract void initVariables();

    protected abstract void initView();

    public abstract void initNet();

    public abstract void initData();

    public abstract void setData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialogProgress.isShowing()) {
            mDialogProgress.dismiss();
        }
    }
}
