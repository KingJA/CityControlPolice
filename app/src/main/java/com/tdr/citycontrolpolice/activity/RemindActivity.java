package com.tdr.citycontrolpolice.activity;

import android.view.View;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 13:19
 * 修改备注：
 */
public class RemindActivity extends BackTitleActivity {
    @Override
    public View setContentView() {
        view=View.inflate(this, R.layout.single_lv_divider,null);
        return view;
    }

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
setTitle("提醒记录");
    }
}
