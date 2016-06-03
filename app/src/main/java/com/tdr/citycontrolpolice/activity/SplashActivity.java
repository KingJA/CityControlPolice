package com.tdr.citycontrolpolice.activity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.update.UpdateManager;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.AppInfoUtil;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.DatebaseManager;
import com.tdr.citycontrolpolice.util.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：Splash页面 初始化数据和检查升级
 * 创建人：KingJA
 * 创建时间：2016/3/28 14:58
 * 修改备注：
 */
public class SplashActivity extends Activity {
    private static final String TAG = "SplashActivity";
    private final long DELAYED_MILLS = 2000;
    private final int CALL_INIT_DB = 4;
    private final int CALL_CHECK_DB_UPDATE = 2;
    private final int CALL_LOGIN = 3;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    private long endTime;
    private long startTime;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.pb_init)
    ProgressBar pbInit;
    private Handler mInitHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CALL_INIT_DB:
                    initDatebase();
                    break;
                case CALL_CHECK_DB_UPDATE:
                    checkDatebaseUpdate();
                    break;
                case CALL_LOGIN:
                    Log.i(TAG, "第3步：进入登录界面");
                    timeToActivity();
                    break;
                case Constants.HANDLER_KEY_GETVERSION_FAIL:
                    Log.i(TAG, "版本升级错误");
                    mInitHandler.sendEmptyMessage(CALL_INIT_DB);
                    break;
                case Constants.HANDLER_KEY_GETVERSION_SUCCESS:
                    double newVersion = Double.parseDouble(msg.obj.toString());
                    int versionCode = AppInfoUtil.getVersionCode();
                    if (newVersion > versionCode) {
                        Log.i(TAG, "有新版本，进行更新");
                        new UpdateManager(SplashActivity.this, newVersion).checkUpdate();
                    }
                    Log.i(TAG, "已经是最新版本");
                    mInitHandler.sendEmptyMessage(CALL_INIT_DB);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        StatusBarCompat.initStatusBar(this);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        tvVersion.setText("当前版本:" + AppInfoUtil.getVersionName());
        rlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mInitHandler.removeCallbacks(skipRunnable);
//                goLoginActivity();
            }
        });

        checkVersionUpdate();
    }


    /**
     * 3秒钟后跳到登录页面
     */
    private void timeToActivity() {
        endTime = System.currentTimeMillis();
        if ((endTime - startTime) < DELAYED_MILLS) {
            mInitHandler.postDelayed(skipRunnable, DELAYED_MILLS - (endTime - startTime));
        } else {
            Log.i(TAG, "else: " + String.valueOf(endTime - startTime));
            goLoginActivity();
        }
    }

    /**
     * 跳到登录页面
     */
    private void goLoginActivity() {
        ActivityUtil.goActivityAndFinish(this, KjLoginActivity.class);
    }

    /**
     * 检查版本更新
     */
    private void checkVersionUpdate() {
        startTime = System.currentTimeMillis();
        Log.i(TAG, "第1步：检查版本更新");
        mInitHandler.sendEmptyMessage(CALL_INIT_DB);
    }

    /**
     * 初始化数据库
     */
    private void initDatebase() {
        Log.i(TAG, "第2步：初始化数据库");
        PoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DatebaseManager.getInstance(getApplicationContext()).copyDataBase("citypolice_wz.db");
                mInitHandler.sendEmptyMessage(CALL_CHECK_DB_UPDATE);
            }
        });

    }

    /**
     * 检查数据库更新
     */
    private void checkDatebaseUpdate() {
        Log.i(TAG, "第3步：检查数据库版本更新");
        mInitHandler.sendEmptyMessage(CALL_LOGIN);
    }

    private Runnable skipRunnable = new Runnable() {
        @Override
        public void run() {
            goLoginActivity();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInitHandler.removeCallbacks(skipRunnable);
    }
}
