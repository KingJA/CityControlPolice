package com.tdr.citycontrolpolice.update;


import android.util.Log;

import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Linus_Xie on 2016/3/12.
 */
public class WebserviceRequest {

    public static final String WEBSERVER_NAMESPACE = "http://tempuri.org/";

    /**
     * 获取版本号
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public VersionInfo getVersionCode(String fileName) {

        SoapObject request = new SoapObject(WEBSERVER_NAMESPACE,
                "GetCode");
        request.addProperty("FileName", fileName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.bodyOut = request;

        MyHttpTransportSE ht = new MyHttpTransportSE(
                Constants.getUpdataUrl());
        String result="";
        try {
            ht.call(WEBSERVER_NAMESPACE + "GetCode", envelope);
             result = ((SoapPrimitive) envelope.getResponse()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showMyToast("服务器升级请求存在异常");
        }
        Log.e("CityControlPolice.apk", result);
        return initVersionInfo(result);
    }

    public VersionInfo initVersionInfo(String response) {
        try {
            JSONArray objs = new JSONArray(response);
            JSONObject jsonObject = objs.getJSONObject(0);
            VersionInfo info = new VersionInfo();
            info.setVersionCode(jsonObject.getDouble("android:versionCode"));
            info.setVersionName(jsonObject.getString("android:versionName"));
            info.setPackageName(jsonObject.getString("package"));
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
