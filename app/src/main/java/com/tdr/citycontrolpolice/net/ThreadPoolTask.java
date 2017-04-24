package com.tdr.citycontrolpolice.net;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tdr.citycontrolpolice.activity.KjLoginActivity;
import com.tdr.citycontrolpolice.base.App;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.util.ActivityManager;
import com.tdr.citycontrolpolice.util.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：WebService管理类
 * 创建人：KingJA
 * 创建时间：2016/3/22 13:08
 * 修改备注：
 */
public class ThreadPoolTask implements Runnable {


    private String token;
    private int encryption;
    private String dataTypeCode;
    private Object privateParam;
    private Class clazz;

    private WebServiceCallBack callBack;
    private Activity activity;
    private int resultCode;
    private ErrorResult errorResult;

    public ThreadPoolTask(Builder builder) {
        this.token = builder.token;
        this.encryption = builder.encryption;
        this.dataTypeCode = builder.dataTypeCode;
        this.privateParam = builder.privateParam;
        this.clazz = builder.clazz;
        this.callBack = builder.callBack;
        this.activity = builder.activity;
    }

    public void execute() {
        PoolManager.getInstance().execute(this);
    }


    @Override
    public void run() {
        Map<String, Object> generalParam = getGeneralParam(token, encryption, dataTypeCode, privateParam);
        Logger.json(new Gson().toJson(generalParam));
        try {
            final String json = WebServiceManager.getInstance().load(generalParam);
            JSONObject rootObject = new JSONObject(json);
            resultCode = rootObject.getInt("ResultCode");
            if (resultCode != 0) {
                Log.i("UNSUCCESS", json);
                Logger.json(json);
                String resultText = rootObject.getString("ResultText");
                String dataTypeCode1 = rootObject.getString("DataTypeCode");
                errorResult = new ErrorResult();
                errorResult.setResultCode(resultCode);
                errorResult.setResultText(resultText);
                errorResult.setDataTypeCode(dataTypeCode1);
                if (callBack != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMyToast(errorResult.getResultText());
                            callBack.onErrorResult(errorResult);
                            if (resultCode == 2) {
                                ActivityManager.getAppManager().finishAllActivity();
                                Intent intent = new Intent(App.getContext(), KjLoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                App.getContext().startActivity(intent);
//                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                            ToastUtil.showMyToast(errorResult.getResultText());
                        }
                    });
                }
            } else {
                Log.i("SUCCESS", json);
                Logger.json(json);

                if (callBack != null) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(json2Bean(json, clazz));
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showMyToast("连接超时");
                }
            });
        }
    }

    public Map<String, Object> getGeneralParam(String token, int encryption, String dataTypeCode, Object privateParam) {
        Gson gson = new Gson();
        String json = gson.toJson(privateParam);
        Log.i("PARAM_JSON", json);

        Map<String, Object> generalParam = new HashMap<>();
        generalParam.put("token", token);
        generalParam.put("encryption", encryption);
        generalParam.put("dataTypeCode", dataTypeCode);
        generalParam.put("content", json);
        return generalParam;

    }


    public static class Builder {
        private String token;
        private int encryption;
        private String dataTypeCode;
        private Object privateParam;
        private Class clazz;
        private WebServiceCallBack callBack;
        private Activity activity;

        public ThreadPoolTask.Builder setGeneralParam(String token, int encryption, String dataTypeCode, Object
                privateParam) {
            this.token = token;
            this.encryption = encryption;
            this.dataTypeCode = dataTypeCode;
            this.privateParam = privateParam;
            return this;
        }

        public <T> ThreadPoolTask.Builder setBeanType(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public <T> ThreadPoolTask.Builder setCallBack(WebServiceCallBack<T> callBack) {
            this.callBack = callBack;
            return this;


        }

        public ThreadPoolTask.Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public ThreadPoolTask build() {
            return new ThreadPoolTask(this);
        }
    }

    private <T> T json2Bean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

}
