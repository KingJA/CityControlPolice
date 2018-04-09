package com.tdr.citycontrolpolice.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AddRoom;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.User_PasswordModifyForJingYong;
import com.tdr.citycontrolpolice.event.RefreshInfoManagerFragment;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class ModifyPwdActivity extends BackTitleActivity {
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtRepeatPassword;
    private TextView mTvConfirm;


    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        mEtOldPassword = (EditText) findViewById(R.id.et_old_password);
        mEtNewPassword = (EditText) findViewById(R.id.et_new_password);
        mEtRepeatPassword = (EditText) findViewById(R.id.et_repeat_password);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onModifyPassword();
            }
        });
    }

    private void onModifyPassword() {
        String oldPassword = mEtOldPassword.getText().toString().trim();
        String newPassword = mEtNewPassword.getText().toString().trim();
        String repeatPassword = mEtRepeatPassword.getText().toString().trim();

        if (!CheckUtil.checkEmpty(oldPassword, "请输入旧密码")
                || !CheckUtil.checkPasswordFormat(oldPassword)
                || !CheckUtil.checkEmpty(newPassword, "请输入新密码")
                || !CheckUtil.checkPasswordFormat(newPassword)
                || !CheckUtil.checkEmpty(repeatPassword, "请输入重复密码")
                || !CheckUtil.checkPasswordFormat(repeatPassword)) {
            return;
        }
        if (!newPassword.equals(repeatPassword)) {
            ToastUtil.showMyToast("重复密码不一样，请重新输入");
            return;
        }
        doModify(oldPassword, newPassword);
    }

    private void doModify(String oldPassword, String newPassword) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("OLDPASSWORD", oldPassword);
        param.put("NEWPASSWORD", newPassword);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0,
                "User_PasswordModifyForJingYong", param)
                .setBeanType(User_PasswordModifyForJingYong.class)
                .setActivity(ModifyPwdActivity.this)
                .setCallBack(new WebServiceCallBack<User_PasswordModifyForJingYong>() {
                    @Override
                    public void onSuccess(User_PasswordModifyForJingYong bean) {
                        setProgressDialog(false);
                        ToastUtil.showMyToast("修改成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("修改密码");
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_modify_password, null);
        return view;
    }
}
