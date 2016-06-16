package com.tdr.citycontrolpolice.activity;


import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.AppInfoUtil;
import com.tdr.citycontrolpolice.util.DatebaseManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：Splash页面 初始化数据和检查升级
 * 创建人：KingJA
 * 创建时间：2016/3/28 14:58
 * 修改备注：
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private final long DELAYED_MILLS = 2000;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        handler.postDelayed(skipRunnable,DELAYED_MILLS);
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
        tvVersion.setText("当前版本:" + AppInfoUtil.getVersionName());
        PoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DatebaseManager.getInstance(getApplicationContext()).copyDataBase("citypolice_wz.db");
            }
        });
    }
    private Runnable skipRunnable = new Runnable() {
        @Override
        public void run() {
            ActivityUtil.goActivityAndFinish(SplashActivity.this, KjLoginActivity.class);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(skipRunnable);
    }
}
