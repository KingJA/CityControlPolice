package com.tdr.citycontrolpolice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.otg.idcard.OTGReadCardAPI;
import com.tdr.citycontrolpolice.util.Constants;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

public class TendencyReadAPI implements Callback {

    private static final String TAG = "TendencyReadAPI";

    private Context mContext;
    private Handler mHandler;

    private NfcAdapter mAdapter = null;
    private PendingIntent pi = null;
    // 滤掉组件无法响应和处理的Intent
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    private Intent inintent = null;

    private Intent intent;

    private Bundle bundle;
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

    public static String tagId = ""; // 卡号

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

    public TendencyReadAPI(Context mContext) {
        super();
        this.mContext = mContext;

        mHandler = new Handler(this);

        IPArray = new ArrayList<String>();
        IPArray.add(remoteIPA);
        IPArray.add(remoteIPB);
        IPArray.add(remoteIPC);

        ReadCardAPI = new OTGReadCardAPI(this.mContext, IPArray);

        intent = new Intent();
        bundle = new Bundle();
    }

    public void NfcReadCard(Intent intent) {

        int i = 1;// 1.身份证读取成功；2.e居卡去读成功；3.非e居卡
        String msg = "";

        String action = intent.getAction();

        tagId = Converter.bytesToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)).toUpperCase();
        System.out.println("卡号：" + tagId);
        if (tagId.length() == 16) {
            if (readflag == 1) {
                i = 3;
                System.out.println("3333333333333");
                return;
            }

            inintent = intent;
            ReadCardAPI.writeFile("come into onNewIntent 2");
            Log.e("开始读卡", "读卡开始");

            // 访问本地数据库，请求身份证数据
            AjaxParams params = new AjaxParams();
            Log.e(TAG, "tagId: "+tagId);
            params.put("identitycardid", tagId);
            params.put("commid", "120");
            Log.e(TAG, "尝试从本地服务器读取："+Constants.getNFCUrl());
            FinalHttp fh = new FinalHttp();
            fh.post(Constants.getNFCUrl(), params, new AjaxCallBack<Object>() {
                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    super.onFailure(t, errorNo, strMsg);
                    Log.e(TAG, "本地服务器读取失败！");
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
                    message.what = NfcConstants.HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS;
                    Bundle bundle = new Bundle();
                    bundle.putString("identityMsg", (String) t);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            });

        } else {
            // 以广播的形式传值
            intent = new Intent(NfcConstants.ACTION_IDENTITY);
            bundle.putString(NfcConstants.STATE, "" + 404);
            intent.putExtra("data", bundle);
            mContext.sendBroadcast(intent);
            System.out.println("刷的可能不是身份证");
        }

        // return i;

    }

    @Override
    public boolean handleMessage(Message msg) {
        int tt;
        switch (msg.what) {
            case NfcConstants.MESSAGE_VALID_NFCBUTTON:
                ReadCardAPI.writeFile("come into MESSAGE_CLEAR_ITEMS 1");
                tt = readresult;
                ReadCardAPI.writeFile("come into MESSAGE_CLEAR_ITEMS 2");
                Log.e(TAG, " 艺数返回参数 tt=" + tt);
                if (tt == 2) {
                    Log.e("t = 2:", "接收数据超时！");
                    NfcConstants.myToast(mContext, "接收数据超时！");
                }
                if (tt == 41) {
                    Log.e("t = 41:", "读卡失败！");
                    NfcConstants.myToast(mContext, "读卡失败！读身份证时，请不要移动身份证！");
                }
                if (tt == 42) {
                    Log.e("t = 42:", "没有找到服务器!");
                    NfcConstants.myToast(mContext, "没有找到服务器!");
                }
                if (tt == 43) {
                    Log.e("tt = 43:", "服务器忙！");
                    NfcConstants.myToast(mContext, "服务器忙！");
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
                    Log.e(TAG, "缓存 URL: "+NfcConstants.URL );
                    fh.post(NfcConstants.URL, params, new AjaxCallBack<Object>() {
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

                // 以广播的形式传值
                intent = new Intent(NfcConstants.ACTION_IDENTITY);
                bundle.putString(NfcConstants.STATE, "" + tt);
                bundle.putString(NfcConstants.TAGID, tagId);
                bundle.putString(NfcConstants.NAME, name);
                bundle.putString(NfcConstants.SEX, sex);
                bundle.putString(NfcConstants.NATION, nation);
                bundle.putString(NfcConstants.BIRTHDAY, birthday);
                bundle.putString(NfcConstants.ADDRESS, address);
                bundle.putString(NfcConstants.IDENTITY, identity);
                bundle.putString(NfcConstants.POLICE, police);
                bundle.putString(NfcConstants.VALIDITY, validity);
                bundle.putString(NfcConstants.DNCODE, DNcode);
                intent.putExtra("data", bundle);
                // mContext.sendBroadcast(intent, NfcConstants.PERMSISION);
                mContext.sendBroadcast(intent);
                System.out.println("发送广播~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                readflag = 0;
                break;

            case NfcConstants.HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS:
                Bundle bundle1 = msg.getData();
                String identityMsg = bundle1.getString("identityMsg");
                System.out.println("本地服务器取来的数据：" + identityMsg);
                try {
                    JSONArray jsonArray = new JSONArray(identityMsg);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String state = jsonObject.getString("state");
                    if (NfcConstants.STATE_ALLOW.equalsIgnoreCase(state)) {
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

                        // 以广播的形式传值
                        intent = new Intent(NfcConstants.ACTION_IDENTITY);

                        intent = new Intent(NfcConstants.ACTION_IDENTITY);
                        bundle.putString(NfcConstants.STATE, "90");
                        bundle.putString(NfcConstants.TAGID, tagId);
                        bundle.putString(NfcConstants.NAME, name);
                        bundle.putString(NfcConstants.SEX, sex);
                        bundle.putString(NfcConstants.NATION, nation);
                        bundle.putString(NfcConstants.BIRTHDAY, birthday);
                        bundle.putString(NfcConstants.ADDRESS, address);
                        bundle.putString(NfcConstants.IDENTITY, identity);
                        bundle.putString(NfcConstants.POLICE, police);
                        bundle.putString(NfcConstants.VALIDITY, validity);
                        bundle.putString(NfcConstants.DNCODE, DNcode);
                        intent.putExtra("data", bundle);
                        // mContext.sendBroadcast(intent, NfcConstants.PERMSISION);
                        mContext.sendBroadcast(intent);
                    } else if (NfcConstants.STATE_NOT_ALLOW.equalsIgnoreCase(state)) {
                        // Log.e(TAG, "获取数据失败！");
                        mHandler.sendEmptyMessage(NfcConstants.HANDLER_KEY_GETIDENTITY_LOCAL_FAIL);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case NfcConstants.HANDLER_KEY_GETIDENTITY_LOCAL_FAIL:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (readflag == 1) {
                            return;
                        }
//                        System.out.println("访问艺数时间：" + System.currentTimeMillis());
                        readflag = 1;
                        ReadCardAPI.setPort(9018);
                        ReadCardAPI.setIP(Constants.getNFCIP());
                        Log.e(TAG, "尝试从艺数读取");
                        Log.e(TAG, "艺数端口:9018");
                        Log.e(TAG, "艺数IP:"+Constants.getNFCIP());
                        // ReadCardAPI.setIP("127.0.0.1");//公安内网
                        readresult = ReadCardAPI.NfcReadCard(inintent);
                        mHandler.sendEmptyMessageDelayed(NfcConstants.MESSAGE_VALID_NFCBUTTON, 0);
                        readflag = 0;
                    }
                });
                thread.start();
                break;

            default:
                break;
        }
        return false;
    }

}
