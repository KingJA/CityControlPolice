package com.tdr.citycontrolpolice.util;

import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/3/22.
 */
public class WebUtil extends Thread {
    private Handler mHandler = null;
    private Map<String, Object> mParam = null;
    private String token = null;
    private int encryption = 0;
    private String dataTypeCode = null;
    private String content = null;
    private int msgWhat;


    public WebUtil(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void run() {
        goToWeb();
    }

    public void send(String token, int encryption, String dataTypeCode, String content, int msgWhat) {

        this.token = token;
        this.encryption = encryption;
        this.dataTypeCode = dataTypeCode;
        this.content = content;
        this.msgWhat = msgWhat;


        if (!this.isAlive()) {
            this.start();
        }
    }

    private void goToWeb() {

        try {
            mParam = new HashMap<String, Object>();
            mParam.put("token", token);
            mParam.put("encryption", encryption);
            mParam.put("dataTypeCode", dataTypeCode);
            mParam.put("content", content);

            String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, mParam);
            JSONObject rootObject = new JSONObject(result);

            int error = rootObject.getInt("ResultCode");
            Log.w("error==", error + "");
            if (mHandler != null) {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("error", error);
                bundle.putString("content", rootObject.getString("Content"));
                msg.setData(bundle);
                msg.what = msgWhat;
                mHandler.sendMessage(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);

        }

    }


}
