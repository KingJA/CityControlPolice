package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.os.Bundle;

import com.tdr.citycontrolpolice.view.ZProgressHUD;

/**
 * 自己定义的 activity
 * zf
 */
public abstract class MyActivity extends Activity {
    public ZProgressHUD zProgressHUD;
    public Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        init_dialog();
        init_view();
        init_data();
    }

    /**
     * 初始化布局
     */
    public abstract void init_view();

    /**
     * 初始化数据
     */
    public abstract void init_data();

    private void init_dialog() {
        //zProgressHUD = ZProgressHUD.getInstance(mActivity);
        zProgressHUD = new ZProgressHUD(MyActivity.this);
        zProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

}
