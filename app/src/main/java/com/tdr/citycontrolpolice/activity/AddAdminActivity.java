package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AddAdminByPolice;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AdminList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.event.AdminListRefreshEvent;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/10/17 13:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddAdminActivity extends BackTitleActivity {
    private EditText mEtAdminName;
    private EditText mEtAdminCard;
    private TextView mTvSubmit;


    private String houseId;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_add_admin, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);
    }

    @Override
    protected void initView() {
        mEtAdminName = (EditText) findViewById(R.id.et_admin_name);
        mEtAdminCard = (EditText) findViewById(R.id.et_admin_card);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
    }

    @Override
    public void initNet() {
    }

    private void upload(String name, String cardId) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("CHINESENAME", name);
        param.put("IDENTITYCARD", cardId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_AddAdminByPolice", param)
                .setBeanType(ChuZuWu_AddAdminByPolice.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_AddAdminByPolice>() {
                    @Override
                    public void onSuccess(ChuZuWu_AddAdminByPolice bean) {
                        EventBus.getDefault().post(new AdminListRefreshEvent());
                        setProgressDialog(false);
                        ToastUtil.showMyToast("添加管理员成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void initData() {
        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtAdminName.getText().toString().trim();
                String cardId = mEtAdminCard.getText().toString().trim().toUpperCase();
                if (CheckUtil.checkEmpty(name, "请输入姓名") && CheckUtil.checkIdCard(cardId, "身份证号格式错误")) {
                    upload(name, cardId);
                }
            }
        });
    }

    @Override
    public void setData() {
        setTitle("添加管理员");
    }

    private static final String HOUSE_ID = "HOUSE_ID";

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, AddAdminActivity.class);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }
}
