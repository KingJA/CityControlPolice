package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebUtil;
import com.tdr.citycontrolpolice.view.ZProgressHUD;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public class ModifyPwdActivity extends Activity {
    private EditText et_oldpassword, et_newpassword, et_password;
    private String oldpassword, newpassword, password;
    private Button bt_submit;
    private final static int SUBMIT = 1001;
    private final static int ERROR = 4001;
    public ZProgressHUD zProgressHUD;
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SUBMIT:
                    Toast.makeText(mContext, msg.getData().getString("resultText"), Toast.LENGTH_SHORT).show();
                    if (msg.getData().getInt("error") == 0) {
                        Intent intent = new Intent(ModifyPwdActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                case 1:
                    Toast.makeText(mContext, msg.getData().getString("resultText"), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifypwd);
        mContext = this;

        title();
        init_view();
    }

    /**
     * 标题栏
     */
    private void title() {
        ImageView img_title = (ImageView) findViewById(R.id.image_back);
        img_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.text_title);
        tv_title.setText("修改密码");
    }

    /**
     * 初始化控件
     */
    private void init_view() {
        zProgressHUD = new ZProgressHUD(this);
        zProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        et_oldpassword = (EditText) findViewById(R.id.et_modify_old);
        et_newpassword = (EditText) findViewById(R.id.et_modify_password);
        et_password = (EditText) findViewById(R.id.et_modify_password2);

        bt_submit = (Button) findViewById(R.id.bt_modify_submit);
        bt_submit.setOnClickListener(new BTOnClick());
    }


    /**
     * 提交按钮
     */
    class BTOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            oldpassword = et_oldpassword.getText().toString().trim();
            password = et_password.getText().toString().trim();
            newpassword = et_newpassword.getText().toString().trim();
            if (oldpassword.equals("")) {
                Toast.makeText(mContext, "请输入旧密码", Toast.LENGTH_SHORT).show();
                return;
            }

            if ((!password.equals(newpassword)) || password.equals("")) {
                Toast.makeText(mContext, "新密码与确认密码输入不正确", Toast.LENGTH_SHORT).show();
                return;
            }

            //submit_info();
        }
    }

    //提交数据
    private void submit_info() {


        final JSONObject object = new JSONObject();
        try {
            object.put("TaskID", "1");
            object.put("OLDPASSWORD", oldpassword);
            object.put("NEWPASSWORD", password);
            Log.e("code", object.toString());

            String token = UserService.getInstance(mContext).getToken();
            int encryption = 0;
            String dataTypeCode = "User_PasswordModify";
            String content = object.toString();

            WebUtil webUtil = new WebUtil(mHandler);
            webUtil.send(token, encryption, dataTypeCode, content, SUBMIT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
