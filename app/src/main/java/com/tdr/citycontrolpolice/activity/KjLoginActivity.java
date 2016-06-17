package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.Param_User_LoginByPolice;
import com.tdr.citycontrolpolice.entity.User_LoginByPolice;
import com.tdr.citycontrolpolice.net.DownloadDbManager;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.update.GetVersionCodeAsynckTask;
import com.tdr.citycontrolpolice.update.UpdateManager;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.AesEncoder;
import com.tdr.citycontrolpolice.util.AppInfoUtil;
import com.tdr.citycontrolpolice.util.AppManager;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.PhoneUtil;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.WebServiceMethod;
import com.tdr.citycontrolpolice.view.KingJA_SwtichButton;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：登录
 * 创建人：KingJA
 * 创建时间：2016/3/28 14:58
 * 修改备注：
 * May you do good and not evil.
 * May you find forgiveness for yourself and forgive others.
 * May you share freely, never taking more than you give.
 */
public class KjLoginActivity extends Activity implements KingJA_SwtichButton.OnSwitchListener {
    private static final String TAG = "KjLoginActivity";
    @Bind(R.id.et_login_name)
    EditText etLoginName;
    @Bind(R.id.et_login_password)
    EditText etLoginPassword;
    @Bind(R.id.cb_remmber)
    CheckBox cbRemmber;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.kj_switchbutton)
    KingJA_SwtichButton kjSwitchbutton;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.v_something)
    View vSomething;

    private String userName;
    private String password;
    private long[] mHits = new long[5];
    private boolean keyPandora = true;
    boolean isLoginByPolice = true;
    private DialogProgress dialogProgress;
    private Handler mInitHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.HANDLER_KEY_GETVERSION_FAIL:
                    Log.i(TAG, "版本升级错误");
                    break;
                case Constants.HANDLER_KEY_GETVERSION_SUCCESS:
                    double newVersion = Double.parseDouble(msg.obj.toString());
                    int versionCode = AppInfoUtil.getVersionCode();
                    if (newVersion > versionCode) {
                        Log.i(TAG, "有新版本，进行更新");
                        new UpdateManager(KjLoginActivity.this, newVersion).checkUpdate();
                    }
                    Log.i(TAG, "已经是最新版本");
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_login_kj);
        ButterKnife.bind(this);
        checkVersionUpdate();
        new DownloadDbManager(mInitHandler).startDownloadDb();
        initView();
        initData();
    }

    private void initData() {
        tvVersion.setText("当前版本:" + AppInfoUtil.getVersionName());
        boolean checked = (boolean) SharedPreferencesUtils.get("login_remember", false);
        if (checked) {
            String login_name = (String) SharedPreferencesUtils.get("login_name", "");
            String login_password = (String) SharedPreferencesUtils.get("login_password", "");
            Log.i(TAG, "login_password: " + login_password);
            isLoginByPolice = (Boolean) SharedPreferencesUtils.get("login_police", true);
            etLoginName.setText(login_name);
            etLoginPassword.setText(AesEncoder.decode(login_password));
            Log.i(TAG, "etLoginPassword: " + AesEncoder.decode(login_password));
            cbRemmber.setChecked(true);
            kjSwitchbutton.setSwitch(isLoginByPolice);
        }

    }

    private void initView() {
        kjSwitchbutton.setOnSwitchListener(this);
        dialogProgress = new DialogProgress(this);
    }

    @OnClick(R.id.btn_login)
    void submit() {
        userName = etLoginName.getText().toString().trim();
        password = etLoginPassword.getText().toString().trim();
        if (CheckUtil.checkEmpty(userName, "用户名不能为空") && CheckUtil.checkPasswordFormat(password)) {
            remember(userName, password, cbRemmber.isChecked());
            onLogin();
        }
    }

    private void remember(String userName, String password, boolean checked) {
        if (checked) {
            SharedPreferencesUtils.put("login_name", userName);
            SharedPreferencesUtils.put("login_password", AesEncoder.encode(password));
            Log.i(TAG, "remember password: " + password);
            Log.i(TAG, "remember AES: " + AesEncoder.encode(password));
            SharedPreferencesUtils.put("login_remember", true);
            SharedPreferencesUtils.put("login_police", isLoginByPolice);
        } else {
            SharedPreferencesUtils.put("login_remember", false);
        }
    }

    private void onLogin() {
        dialogProgress.show();
        String methodName = isLoginByPolice ? WebServiceMethod.User_LoginByPolice : WebServiceMethod.User_LoginByAccount;
        Param_User_LoginByPolice paramLogin = buildParam();
        new ThreadPoolTask.Builder()
                .setGeneralParam("", 0, methodName, paramLogin)
                .setBeanType(User_LoginByPolice.class)
                .setCallBack(new WebServiceCallBack<User_LoginByPolice>() {
                    @Override
                    public void onSuccess(User_LoginByPolice bean) {
                        dialogProgress.dismiss();
                        savaDateToLocal(bean);
                        ActivityUtil.goActivityAndFinish(KjLoginActivity.this, HomeActivity.class);

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        dialogProgress.dismiss();
                    }
                }).build().execute();

    }

    private void savaDateToLocal(User_LoginByPolice bean) {
        User_LoginByPolice.ContentBean content = bean.getContent();
        SharedPreferencesUtils.put("login_name", userName);
        SharedPreferencesUtils.put("uid", content.getUserID());
        SharedPreferencesUtils.put("token", content.getToken());
    }

    private Param_User_LoginByPolice buildParam() {
        Param_User_LoginByPolice paramLogin = new Param_User_LoginByPolice();
        Param_User_LoginByPolice.PHONEINFOBean phoneInfo = new PhoneUtil(this).getPhoneInfo();
        paramLogin.setUSERNAME(userName);
        paramLogin.setPASSWORD(password);
        paramLogin.setPHONEINFO(phoneInfo);
        paramLogin.setSOFTTYPE(2);
        paramLogin.setSOFTVERSION(1.1);
        return paramLogin;
    }

    private void checkVersionUpdate() {
        GetVersionCodeAsynckTask asynckTask = new GetVersionCodeAsynckTask(
                KjLoginActivity.this, mInitHandler);
        asynckTask.execute(UpdateManager.UPDATE_APKNAME);
    }


    @Override
    public void onSwitch(boolean isLeft) {
        this.isLoginByPolice = isLeft;
    }


    @OnClick(R.id.v_something)
    void openPandora() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            if (keyPandora) {
                ActivityUtil.goActivity(KjLoginActivity.this, GmActivity.class);
                keyPandora = false;
                vSomething.setClickable(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
