package com.tdr.citycontrolpolice.activity;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Common_IdentityCardAuthentication;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.tendencynfc.TendencyReadAPI;
import com.tdr.tendencynfc.util.Constants;

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
    private TendencyReadAPI mRead;
    private String tagId;
    private NfcAdapter mAdapter = null;
    private PendingIntent pi = null;
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    private Intent inintent = null;

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
                submit();
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
    private void setCardInfo(String name, String sex, String nation, String birthday, String address, String identity) {
        tv_name.setText(name);
        tv_card.setText(identity);
        tv_nation.setText(nation);
        tv_birthday.setText(birthday);
        tv_address.setText(address);
        tv_gender.setText(sex);
        tv_card_no.setText(tagId);
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
            ThreadPoolTask.Builder<Common_IdentityCardAuthentication> builder = new ThreadPoolTask.Builder<Common_IdentityCardAuthentication>();
            ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(NfcActivity.this).getToken(), 0, "Common_IdentityCardAuthentication", param)
                    .setBeanType(Common_IdentityCardAuthentication.class)
                    .setActivity(NfcActivity.this)
                    .setCallBack(new WebServiceCallBack<Common_IdentityCardAuthentication>() {
                        @Override
                        public void onSuccess(Common_IdentityCardAuthentication bean) {
                            ToastUtil.showMyToast("完成审核");
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
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
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
        mRead.NfcReadCard(intent, com.tdr.citycontrolpolice.util.Constants.NFC_URL, com.tdr.citycontrolpolice.util.Constants.NFC_IP);

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
            String action = intent.getAction();
            if (action.equals("com.tdr.identity.readcard")) {// 身份证
                Bundle bundle = intent.getBundleExtra("data");
                String tt = bundle.getString(Constants.STATE);
                if (tt.equals("2")) {
                    ToastUtil.showMyToast("接收数据超时");
                } else if (tt.equals("41")) {
                    ToastUtil.showMyToast("读卡失败！读身份证时，请不要移动身份证");
                } else if (tt.equals("42")) {
                    ToastUtil.showMyToast("没有找到服务器");
                } else if (tt.equals("43")) {
                    ToastUtil.showMyToast("服务器忙");
                } else if (tt.equals("90")) {
                    String tagId = bundle.getString(Constants.TAGID, "unknown");
                    String name = bundle.getString(Constants.NAME, "unknown");
                    String sex = bundle.getString(Constants.SEX, "unknown");
                    String nation = bundle.getString(Constants.NATION, "unknown");
                    String birthday = bundle.getString(Constants.BIRTHDAY, "unknown");
                    String address = bundle.getString(Constants.ADDRESS, "unknown");
                    String identity = bundle.getString(Constants.IDENTITY, "unknown");
                    String police = bundle.getString(Constants.POLICE, "unknown");
                    String validity = bundle.getString(Constants.VALIDITY, "unknown");
                    String dncode = bundle.getString(Constants.DNCODE, "unknown");
                    setCardInfo(name, sex, nation, birthday, address, identity);
                    Log.i(TAG, "身份证: " + "身份证卡片ID：" + tagId + "\r\n" + "姓名：" + name + "\r\n" + "性别：" + sex + "\r\n" + "民族：" + nation
                            + "\r\n" + "出生年月：" + birthday + "\r\n" + "地址：" + address + "\r\n" + "身份证号码：" + identity
                            + "\r\n" + "派出所：" + police + "\r\n" + "有效期：" + validity + "\r\n" + "DN码：" + dncode);
                }
            } else if (action.equals("com.tdr.ecard.readcard")) {// E居卡
                Bundle bundle = intent.getBundleExtra("data");
                String e_tagId = bundle.getString(Constants.TAGID);
                String e_idCard = bundle.getString(Constants.ID_CARD);
                String e_phoneNum = bundle.getString(Constants.PHONE_NUM);
                String e_name = bundle.getString(Constants.E_NAME);
                String e_childId = bundle.getString(Constants.CHILD_ID);
                String e_childName = bundle.getString(Constants.CHILD_NAME);
                String e_currentAddress = bundle.getString(Constants.CURRENT_ADDRESS);
                String e_primaryAddress = bundle.getString(Constants.PRIMARY_ADDRESS);
                Log.i(TAG, "E居卡: " + "E居卡ID：" + e_tagId + "\r\n" + "身份证号码：" + e_idCard + "\r\n" + "手机号码：" + e_phoneNum + "\r\n" + "姓名："
                        + e_name + "\r\n" + "儿童身份证号码：" + e_childId + "\r\n" + "儿童姓名：" + e_childName + "\r\n"
                        + "现住址：" + e_currentAddress + "\r\n" + "户籍住址：" + e_primaryAddress);
            }
        }
    };

}



