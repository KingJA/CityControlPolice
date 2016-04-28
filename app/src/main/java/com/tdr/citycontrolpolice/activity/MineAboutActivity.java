package com.tdr.citycontrolpolice.activity;

import android.view.View;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.AppInfoUtil;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：关于我们
 * 创建人：KingJA
 * 创建时间：2016/4/26 11:01
 * 修改备注：
 */
public class MineAboutActivity extends BackTitleActivity {

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_about, null);
        return view;
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        TextView tv_version = (TextView) view.findViewById(R.id.tv_version);
        tv_version.setText("当前软件版本:" + AppInfoUtil.getVersionName());
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("关于我们");
    }


}
