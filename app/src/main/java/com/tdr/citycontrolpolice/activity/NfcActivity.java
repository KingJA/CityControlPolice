package com.tdr.citycontrolpolice.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.otg.idcard.OTGReadCardAPI;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Common_IdentityCardAuthentication;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.NfcConstants;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.tendencynfc.TendencyReadAPI;
import com.tdr.tendencynfc.util.Constants;
import com.tdr.tendencynfc.util.Converter;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/6 17:03
 * 修改备注：
 */
public class NfcActivity extends BackTitleActivity implements Handler.Callback {

    private static final String TAG = "NfcActivity";
    private TextView tv_gender;
    private TextView tv_address;
    private TextView tv_birthday;
    private TextView tv_nation;
    private TextView tv_card;
    private TextView tv_name;
    private TextView tv_card_no;
    private Button btn_submit;

    private Context mContext;
    private Handler mHandler;

    private TendencyReadAPI mRead;

    private NfcAdapter mAdapter = null;
    private PendingIntent pi = null;
    // 滤掉组件无法响应和处理的Intent
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    private Intent inintent = null;

    /**
     * 艺数
     */
    private int readflag = 0;
    private int readresult = 0;
    private ArrayList<String> IPArray = null;
    public static String remoteIPA = "";
    public static String remoteIPB = "";
    public static String remoteIPC = "";

    private OTGReadCardAPI ReadCardAPI;

    private String tagId = "";

    /**
     * 身份证相关字段
     */
    public static String name = "";
    public static String sex = "";
    public static String nation = "";
    public static String birthday = "";
    public static String address = "";
    public static String identity = "";
    public static String police = "";
    public static String validity = "";
    public static String DNcode = "";

    /**
     * E居卡相关字段
     */
    public static String CARD_ID = "";
    public static String CARD_TYPE = "";
    public static String ID_CARD = "";
    public static String PHONE_NUM = "";
    public static String NAME = "";
    public static String CHILD_ID = "";
    public static String CHILD_NAME = "";
    public static String CURRENT_ADDRESS = "";
    public static String PRIMARY_ADDRESS = "";

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
        btn_submit = (Button) view.findViewById(R.id.btn_submit);

    }


    @Override
    public void initNet() {

    }

    @Override
    public void initData() {

        mContext = this;
        mHandler = new Handler(this);

        mRead = new TendencyReadAPI(mContext);

        IPArray = new ArrayList<String>();
        IPArray.add(remoteIPA);
        IPArray.add(remoteIPB);
        IPArray.add(remoteIPC);


        ReadCardAPI = new OTGReadCardAPI(getApplicationContext(), IPArray);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this, "当前设备不支持nfc", Toast.LENGTH_SHORT).show();
        } else {
            initNfc();
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    @Override
    public void setData() {
        setTitle("NFC人员信息");
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
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mTechLists = new String[][]{new String[]{NfcB.class.getName()}, new String[]{NfcA.class.getName()},
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
    public boolean handleMessage(Message msg) {
        int tt;
        switch (msg.what) {
            case NfcConstants.HANDLER_KEY_GETIDENTITY_LOCAL_FAIL:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (readflag == 1) {
                            return;
                        }
                        System.out.println("访问艺数时间：" + System.currentTimeMillis());
                        readflag = 1;
                        ReadCardAPI.setPort(9018);
                        // ReadCardAPI.setIP("103.21.119.78");
                        ReadCardAPI.setIP(com.tdr.citycontrolpolice.util.Constants.NFC_IP);
                        // ReadCardAPI.setIP("172.18.18.38");//公安省厅
                        readresult = ReadCardAPI.NfcReadCard(inintent);
                        mHandler.sendEmptyMessageDelayed(Constants.MESSAGE_VALID_NFCBUTTON, 0);
                        readflag = 0;
                    }
                });
                thread.start();

                break;

            case Constants.MESSAGE_VALID_NFCBUTTON:
                ReadCardAPI.writeFile("come into MESSAGE_CLEAR_ITEMS 1");
                tt = readresult;
                ReadCardAPI.writeFile("come into MESSAGE_CLEAR_ITEMS 2");
                Log.e("For Test", " ReadCard TT=" + tt);
                if (tt == 2) {
                    Log.e("t = 2:", "接收数据超时！");
                }
                if (tt == 41) {
                    Log.e("t = 41:", "读卡失败！");
                }
                if (tt == 42) {
                    Log.e("t = 42:", "没有找到服务器!");
                }
                if (tt == 43) {
                    Log.e("tt = 43:", "服务器忙！");
                }
                if (tt == 90) {
                    name = ReadCardAPI.Name();
                    System.out.println("姓名1：" + name);
                    sex = ReadCardAPI.SexL();
                    System.out.println("性别1：" + sex);
                    nation = ReadCardAPI.NationL();
                    System.out.println("民族1：" + nation);
                    birthday = ReadCardAPI.Born();
                    System.out.println("出生年月1：" + birthday);
                    address = ReadCardAPI.Address();
                    System.out.println("地址1：" + address);
                    identity = ReadCardAPI.CardNo();
                    System.out.println("身份证号1：" + identity);
                    police = ReadCardAPI.Police();
                    System.out.println("派出所1：" + police);
                    validity = ReadCardAPI.ActivityL();
                    System.out.println("有效期1：" + validity);
                    DNcode = ReadCardAPI.DNcode();
                    System.out.println("DN码1：" + DNcode);
                    setCardInfo(name, sex, nation, birthday, address, identity);


                    AjaxParams params = new AjaxParams();
                    params.put("commid", "121");
                    params.put("identitycardid", tagId);
                    params.put("owername", name);
                    params.put("sex", sex);
                    params.put("nation", nation);
                    params.put("birthday", birthday);
                    params.put("hjaddress", address);
                    params.put("identitycard", identity);
                    params.put("police", police);
                    params.put("validity", validity);
                    params.put("dncode", DNcode);
                    params.put("status", "1");
                    params.put("source", "3");
                    FinalHttp fh = new FinalHttp();
                    fh.post(com.tdr.citycontrolpolice.util.Constants.NFC_URL, params, new AjaxCallBack<Object>() {
                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            super.onFailure(t, errorNo, strMsg);
                            Log.e(TAG, "添加身份证信息至数据库失败！");
                        }

                        @Override
                        public void onLoading(long count, long current) {
                            super.onLoading(count, current);
                        }

                        @Override
                        public void onSuccess(Object t) {
                            super.onSuccess(t);
                            System.out.println("插入数据：" + (String) t);
                            Log.e(TAG, "添加身份证信息至数据库成功！" + "~~~Success~~~");
                        }
                    });
                }

                readflag = 0;
                break;

            case Constants.HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS:
                Bundle bundle1 = msg.getData();
                String identityMsg = bundle1.getString("identityMsg");
                System.out.println("本地服务器取来的数据：" + identityMsg);
                try {
                    JSONArray jsonArray = new JSONArray(identityMsg);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String state = jsonObject.getString("state");
                    if (Constants.STATE_ALLOW.equalsIgnoreCase(state)) {
                        name = jsonObject.getString("owername");
                        System.out.println("姓名：" + name);
                        sex = jsonObject.getString("sex");
                        System.out.println("性别：" + sex);
                        nation = jsonObject.getString("nation");
                        System.out.println("民族：" + nation);
                        birthday = jsonObject.getString("birthday");
                        System.out.println("出生年月：" + birthday);
                        address = jsonObject.getString("hjaddress");
                        System.out.println("地址：" + address);
                        identity = jsonObject.getString("identitycard");
                        System.out.println("身份证号：" + identity);
                        police = jsonObject.getString("police");
                        System.out.println("派出所：" + police);
                        validity = jsonObject.getString("validity");
                        System.out.println("有效期：" + validity);
                        DNcode = jsonObject.getString("dncode");
                        System.out.println("DN码：" + DNcode);
                        setCardInfo(name, sex, nation, birthday, address, identity);
                    } else if (Constants.STATE_NOT_ALLOW.equalsIgnoreCase(state)) {
                        Log.e(TAG, "获取数据失败！");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        tagId = Converter.bytesToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)).toUpperCase();
        Log.e(TAG, "" + tagId.length());

        if (tagId.length() == 8) { // E居卡
            mRead.NfcReadCard(intent);
        } else if (tagId.length() == 16) {
            if (readflag == 1) {
                return;
            }

            inintent = intent;
            ReadCardAPI.writeFile("come into onNewIntent 2");
            Log.e("开始读卡", "读卡开始");

            // 访问本地数据库，请求身份证数据
            AjaxParams params = new AjaxParams();
            params.put("identitycardid", tagId);
            params.put("commid", "120");
            FinalHttp fh = new FinalHttp();
            fh.post(com.tdr.citycontrolpolice.util.Constants.NFC_URL, params, new AjaxCallBack<Object>() {
                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                    Log.e(TAG, "访问本地库失败！");
                    /**
                     * 访问艺数解析接口
                     */
                    Message msg = new Message();
                    mHandler.sendEmptyMessage(NfcConstants.HANDLER_KEY_GETIDENTITY_LOCAL_FAIL);
                }

                @Override
                public void onStart() {
                    super.onStart();
                    System.out.println("本地库时间：" + System.currentTimeMillis());
                }

                @Override
                public void onLoading(long count, long current) {
                    super.onLoading(count, current);
                }

                @Override
                public void onSuccess(Object t) {
                    super.onSuccess(t);
                    System.out.println("访问结果：" + (String) t);

                    Message message = new Message();
                    message.what = Constants.HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS;
                    Bundle bundle = new Bundle();
                    bundle.putString("identityMsg", (String) t);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            });

        }

    }
}
