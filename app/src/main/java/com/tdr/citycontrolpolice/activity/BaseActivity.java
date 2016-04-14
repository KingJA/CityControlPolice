package com.tdr.citycontrolpolice.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 9:56
 * 修改备注：
 */
public abstract class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initView();
        initNet();
        initData();
        setData();
    }

    public abstract void initVariables();

    protected abstract void initView();

    public abstract void initNet();

    public abstract void initData();

    public abstract void setData();
}
