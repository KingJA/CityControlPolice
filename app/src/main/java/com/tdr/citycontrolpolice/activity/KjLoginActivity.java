package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.Param_User_LoginByPolice;
import com.tdr.citycontrolpolice.entity.User_LoginByPolice;
import com.tdr.citycontrolpolice.materialedittext.MaterialEditText;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.AppInfoUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.PhoneUtil;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.view.KingJA_SwtichButton;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;
import com.tdr.citycontrolpolice.view.dialog.DialogProgressAll;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：登录
 * 创建人：KingJA
 * 创建时间：2016/3/28 14:58
 * 修改备注：
 */
public class KjLoginActivity extends Activity implements KingJA_SwtichButton.OnSwitchListener {
    private static final String TAG = "KjLoginActivity";
    @Bind(R.id.et_login_name)
    MaterialEditText etLoginName;
    @Bind(R.id.et_login_password)
    MaterialEditText etLoginPassword;
    @Bind(R.id.cb_remmber)
    CheckBox cbRemmber;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.kj_switchbutton)
    KingJA_SwtichButton kjSwitchbutton;
    @Bind(R.id.tv_version)
    TextView tvVersion;

    private String userName;
    private String password;
    boolean isLoginByPolice = true;
    private DialogProgress dialogProgress;
    private DialogProgressAll dialogProgressAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kj);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 初始化记住按钮
     */
    private void initData() {
        tvVersion.setText("当前版本:" + AppInfoUtil.getVersionName());
        boolean checked = (boolean) SharedPreferencesUtils.get("login_remember", false);
        if (checked) {
            String login_name = (String) SharedPreferencesUtils.get("login_name", "");
            String login_password = (String) SharedPreferencesUtils.get("login_password", "");
            etLoginName.setText(login_name);
            etLoginPassword.setText(login_password);
            cbRemmber.setChecked(true);
        }

    }

    private void initView() {
        kjSwitchbutton.setOnSwitchListener(this);
        dialogProgress = new DialogProgress(this);
        dialogProgressAll = new DialogProgressAll(this);
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
            Log.i(TAG, "userName: " + userName);
            SharedPreferencesUtils.put("login_name", userName);
            SharedPreferencesUtils.put("login_password", password);
            SharedPreferencesUtils.put("login_remember", true);
        } else {
            SharedPreferencesUtils.put("login_remember", false);
        }
    }

    /**
     * 登录
     */
    private void onLogin() {
        dialogProgress.show();
        String methodName = isLoginByPolice ? "User_LoginByPolice" : "User_LoginByAccount";
        Param_User_LoginByPolice paramLogin = buildParam();
        ThreadPoolTask.Builder<User_LoginByPolice> builder = new ThreadPoolTask.Builder<User_LoginByPolice>();
        ThreadPoolTask task = builder.setGeneralParam("", 0, methodName, paramLogin)
                .setBeanType(User_LoginByPolice.class)
                .setActivity(KjLoginActivity.this)
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
                }).build();
        PoolManager.getInstance().execute(task);

    }

    /**
     * 保存本地XML
     *
     * @param bean
     */
    private void savaDateToLocal(User_LoginByPolice bean) {
        User_LoginByPolice.ContentBean content = bean.getContent();
        SharedPreferencesUtils.put("uid", content.getUserID());
        SharedPreferencesUtils.put("token", content.getToken());
    }

    /**
     * 获取参数
     *
     * @return
     */
    @NonNull
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


    @Override
    public void onSwitch(boolean isLeft) {
        this.isLoginByPolice = isLeft;
    }

}
