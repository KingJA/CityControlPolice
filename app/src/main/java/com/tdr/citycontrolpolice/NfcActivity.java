package com.tdr.citycontrolpolice;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.activity.BackTitleActivity;
import com.tdr.citycontrolpolice.entity.Common_IdentityCardAuthentication;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：NFC 页面
 * 创建人：KingJA
 * 创建时间：2016/4/6 17:03
 * 修改备注：
 */
public class NfcActivity extends BackTitleActivity {

    private static final String TAG = "NfcActivity";
    private TextView tv_gender;
    private TextView tv_address;
    private TextView tv_birthday;
    private TextView tv_nation;
    private TextView tv_card;
    private TextView tv_name;
    private TextView tv_card_no;
    private TextView tv_submit;
    private TextView tv_police;
    private TextView tv_validity;
    private TextView tv_dncode;
    private TendencyReadAPI mRead;
    private NfcAdapter mAdapter = null;
    private PendingIntent pi = null;
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_mynfc, null);
        return view;
    }

    @Override
    public void initVariables() {
    }

    @Override
    protected void initView() {
        tv_card_no = (TextView) view.findViewById(R.id.tv_card_no);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_card = (TextView) view.findViewById(R.id.tv_card);
        tv_nation = (TextView) view.findViewById(R.id.tv_nation);
        tv_birthday = (TextView) view.findViewById(R.id.tv_birthday);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_gender = (TextView) view.findViewById(R.id.tv_gender);
        tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_police = (TextView) view.findViewById(R.id.tv_police);
        tv_validity = (TextView) view.findViewById(R.id.tv_validity);
        tv_dncode = (TextView) view.findViewById(R.id.tv_dncode);
        registerNfcReceiver();
    }


    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        mRead = new TendencyReadAPI(this);
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this, "当前设备不支持nfc", Toast.LENGTH_SHORT).show();
        } else {
            initNfc();
        }
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showMyToast("完成审核");
                finish();
            }
        });
    }

    @Override
    public void setData() {
        setTitle("NFC人员认证");
    }


    /**
     * 在布局显示身份证信息
     */
    private void setCardInfo(String tagId, String name, String sex, String nation, String birthday, String address, String identity, String police, String validity, String dncode) {
        tv_name.setText(name);
        tv_card.setText(identity);
        tv_nation.setText(nation);
        tv_birthday.setText(birthday);
        tv_address.setText(address);
        tv_gender.setText(sex);
        tv_card_no.setText(tagId);
        tv_police.setText(police);
        tv_validity.setText(validity);
        tv_dncode.setText(dncode);
    }

    /**
     * 上传身份证信息
     */
    private void submit() {
        String name = tv_name.getText().toString().trim();
        String gender = tv_gender.getText().toString().trim();
        String nation = tv_nation.getText().toString().trim();
        String birthday = tv_birthday.getText().toString().trim();
        String address = tv_address.getText().toString().trim();
        String cardNO = tv_card.getText().toString().trim();
        String cardID = tv_card_no.getText().toString().trim();
        if (CheckUtil.checkEmpty(cardNO, "未获取信息")) {
            Map<String, Object> param = new HashMap<>();
            param.put("TaskID", "1");
            param.put("NAME", name);
            param.put("SEX", gender);
            param.put("NATION", nation);
            param.put("BIRTHDAY", birthday);
            param.put("ADDRESS", address);
            param.put("IDENTITYCARD", cardNO);
            param.put("IDENTITYCARDID", cardID);
            ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
            ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(NfcActivity.this).getToken(), 0, "Common_IdentityCardAuthentication", param)
                    .setBeanType(Common_IdentityCardAuthentication.class)
                    .setActivity(NfcActivity.this)
                    .setCallBack(new WebServiceCallBack<Common_IdentityCardAuthentication>() {
                        @Override
                        public void onSuccess(Common_IdentityCardAuthentication bean) {
//                            ToastUtil.showMyToast("完成审核");
                            finish();
                        }

                        @Override
                        public void onErrorResult(ErrorResult errorResult) {

                        }
                    }).build();
            PoolManager.getInstance().execute(task);
        }
    }


    private void initNfc() {
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mTechLists = new String[][]{new String[]{NfcB.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{MifareClassic.class.getName()}};
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            stopNfcListener();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null)
            startNfcListener();
    }

    private void startNfcListener() {
        mAdapter.enableForegroundDispatch(this, pi, new IntentFilter[]{tagDetected}, mTechLists);
    }

    private void stopNfcListener() {
        mAdapter.disableForegroundDispatch(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        unregisterReceiver(nfcReceiver);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: ");
        mRead.NfcReadCard(intent);
        setProgressDialog(true);

    }

    public void registerNfcReceiver() {
        IntentFilter intentFilter = new IntentFilter("com.tdr.identity.readcard");
        this.registerReceiver(nfcReceiver, intentFilter);
        intentFilter = new IntentFilter("com.tdr.ecard.readcard");
        this.registerReceiver(nfcReceiver, intentFilter);
    }

    private BroadcastReceiver nfcReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            setProgressDialog(false);
            String action = intent.getAction();
            if (action.equals("com.tdr.identity.readcard")) {
                Bundle bundle = intent.getBundleExtra("data");
                String tt = bundle.getString(NfcConstants.STATE);
                if (tt.equals("2")) {
                    ToastUtil.showMyToast("接收数据超时");
                } else if (tt.equals("41")) {
                    ToastUtil.showMyToast("读卡失败！读身份证时，请不要移动身份证");
                } else if (tt.equals("42")) {
                    ToastUtil.showMyToast("没有找到服务器");
                } else if (tt.equals("43")) {
                    ToastUtil.showMyToast("服务器忙");
                } else if (tt.equals("404")) {
                    ToastUtil.showMyToast("请使用身份证刷卡");
                } else if (tt.equals("90")) {
                    String tagId = bundle.getString(NfcConstants.TAGID, "unknown");
                    String name = bundle.getString(NfcConstants.NAME, "unknown");
                    String sex = bundle.getString(NfcConstants.SEX, "unknown");
                    String nation = bundle.getString(NfcConstants.NATION, "unknown");
                    String birthday = bundle.getString(NfcConstants.BIRTHDAY, "unknown");
                    String address = bundle.getString(NfcConstants.ADDRESS, "unknown");
                    String identity = bundle.getString(NfcConstants.IDENTITY, "unknown");
                    String police = bundle.getString(NfcConstants.POLICE, "unknown");
                    String validity = bundle.getString(NfcConstants.VALIDITY, "unknown");
                    String dncode = bundle.getString(NfcConstants.DNCODE, "unknown");
                    setCardInfo(tagId, name, sex, nation, birthday, address, identity, police, validity, dncode);
                    Log.i(TAG, "身份证: " + "身份证卡片ID：" + tagId + "\r\n" + "姓名：" + name + "\r\n" + "性别：" + sex + "\r\n" + "民族：" + nation
                            + "\r\n" + "出生年月：" + birthday + "\r\n" + "地址：" + address + "\r\n" + "身份证号码：" + identity
                            + "\r\n" + "派出所：" + police + "\r\n" + "有效期：" + validity + "\r\n" + "DN码：" + dncode);
                }
            }
        }
    };

    /**
     * 19900909->1990-09-09
     *
     * @return
     */
    public String getFormatDate(String time) {
        Log.i(TAG, "getFormatDate: " + time);
        if (TextUtils.isEmpty(time)) {

        }
        return time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
    }

}



