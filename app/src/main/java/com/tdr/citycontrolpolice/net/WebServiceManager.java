package com.tdr.citycontrolpolice.net;

import android.util.Log;

import com.google.gson.Gson;
import com.tdr.citycontrolpolice.util.Constants;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：WebService管理类
 * 创建人：KingJA
 * 创建时间：2016/3/22 13:08
 * 修改备注：
 */
public class WebServiceManager {
    private static final String SERVER_URL = Constants.WEBSERVER_URL;//Url
    private static final String SERVER_NAMESPACE = "http://tempuri.org/";//命名空间
    private static final String SERVER_METHOD = "RERequest";// 治安防控APP接口
    private static final String TAG = "WebServiceManager";

    private WebServiceManager() {
    }

    private static WebServiceManager mWebServiceManager;

    public static WebServiceManager getInstance() {
        if (mWebServiceManager == null) {
            synchronized (WebServiceManager.class) {
                if (mWebServiceManager == null) {
                    mWebServiceManager = new WebServiceManager();
                }
            }
        }
        return mWebServiceManager;
    }

    public static String load(Map<String, Object> paramMap) throws IOException, XmlPullParserException {
        HttpTransportSE mHttpTransportSE = new HttpTransportSE(SERVER_URL, 20000);
        SoapObject mSoapObject = new SoapObject(SERVER_NAMESPACE, SERVER_METHOD);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            mSoapObject.addProperty(entry.getKey(), entry.getValue());
        }
        SoapSerializationEnvelope mSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        mSoapSerializationEnvelope.dotNet = true;
        mSoapSerializationEnvelope.bodyOut = mSoapObject;
        mHttpTransportSE.call(SERVER_NAMESPACE + SERVER_METHOD, mSoapSerializationEnvelope);
        SoapPrimitive retult = (SoapPrimitive) mSoapSerializationEnvelope.getResponse();
        return retult.toString();
    }

    public static <T> T load(Map<String, Object> paramMap, Class<T> clazz) throws IOException, XmlPullParserException {
        HttpTransportSE mHttpTransportSE = new HttpTransportSE(SERVER_URL);
        SoapObject mSoapObject = new SoapObject(SERVER_NAMESPACE, SERVER_METHOD);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            mSoapObject.addProperty(entry.getKey(), entry.getValue());
        }
        SoapSerializationEnvelope mSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        mSoapSerializationEnvelope.dotNet = true;
        mSoapSerializationEnvelope.bodyOut = mSoapObject;
        mHttpTransportSE.call(SERVER_NAMESPACE + SERVER_METHOD, mSoapSerializationEnvelope);
        SoapPrimitive result = (SoapPrimitive) mSoapSerializationEnvelope.getResponse();
        Log.i(TAG, "retult: " + result.toString());
        return json2Bean(result.toString(), clazz);

    }

    public static <T> T json2Bean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

}
