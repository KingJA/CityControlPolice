package com.tdr.citycontrolpolice.activity;

import android.view.View;

import com.tdr.citycontrolpolice.R;

public class ModifyPwdActivity extends BackTitleActivity {
    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("修改密码");
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_modify_password, null);
        return view;
    }
}
