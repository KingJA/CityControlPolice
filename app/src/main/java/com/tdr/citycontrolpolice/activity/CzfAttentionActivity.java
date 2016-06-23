package com.tdr.citycontrolpolice.activity;

import android.view.View;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 10:20
 * 修改备注：
 */
public class CzfAttentionActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener{
    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_attention, null);
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
        setOnRightClickListener(this);

    }

    @Override
    public void setData() {
        setTitle("出租房关注");
        setRightImageVisibility(R.drawable.bg_attention);

    }

    @Override
    public void onRightClick() {
//        ActivityUtil.goActivity(this,RemindActivity.class);
        ActivityUtil.goActivity(this,AttentionEditActivity.class);
    }
}
