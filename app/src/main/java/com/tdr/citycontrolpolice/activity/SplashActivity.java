package com.tdr.citycontrolpolice.activity;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.User_LoginByPStore;
import com.tdr.citycontrolpolice.entity.User_LoginByPolice;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.GoUtil;
import com.tdr.citycontrolpolice.util.AppInfoUtil;
import com.tdr.citycontrolpolice.util.DatebaseManager;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.cybertech.pdk.UserInfo;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

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
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        handler.postDelayed(skipRunnable, DELAYED_MILLS);
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
            if (Constants.RELEASE_SERVICE != 1) {
                GoUtil.goActivityAndFinish(SplashActivity.this, KjLoginActivity.class);
            } else {
                loginPstore();
            }

        }
    };

    private void loginPstore() {
        Map<String, String> map = new UserInfo().getUserInfo(this);
        Log.e(TAG, "民警信息=" + map.toString());
        String uid = map.get("uid"); // 用户信息
        String account = map.get("account"); // 账户名
        String dept_id = map.get("dept_id");// 部门ID
        String dept_name = map.get("dept_name");// 部门名称
        String token = map.get("token");// 部门名称

        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", 1);
        param.put("TOKEN", token);
        param.put("SOFTVERSION", 1.1);
        param.put("SOFTTYPE", 3);
        param.put("DEPID", dept_id);
        param.put("DEPNAME", dept_name);
        param.put("UID", uid);
        param.put("ACCOUNT", account);
//        {"TaskID":1,"TOKEN":"","SOFTVERSION":1.1,"SOFTTYPE":3,"DEPID":"330300450000","DEPNAME":"浙江省温州市公安局基层基础支队","UID":"51152","ACCOUNT":"030263"}
        new ThreadPoolTask.Builder()
                .setGeneralParam("", 0, "User_LoginByPStore", param)
                .setBeanType(User_LoginByPStore.class)
                .setCallBack(new WebServiceCallBack<User_LoginByPStore>() {
                    @Override
                    public void onSuccess(User_LoginByPStore bean) {
                        if (bean != null) {
                            savaDateToLocal(bean);
                            GoUtil.goActivityAndFinish(SplashActivity.this, HomeActivity.class);
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        ToastUtil.showMyToast("Pstore登录失败");
                    }
                }).build().execute();

    }

    private void savaDateToLocal(User_LoginByPStore bean) {
        User_LoginByPStore.ContentBean content = bean.getContent();
        SharedPreferencesUtils.put("login_name", content.getNAME());
        SharedPreferencesUtils.put("uid", content.getUSERID());
        SharedPreferencesUtils.put("token", content.getTOKEN());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(skipRunnable);
    }
}
