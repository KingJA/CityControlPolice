package com.tdr.citycontrolpolice.util;

import com.tdr.citycontrolpolice.update.MyHttpTransportSE;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;

/**
 * .
 *
 * @author zf
 */
public class WebService {
    //封装请求(必须在子线程中)
    public static String info(String nanmespace, String name, Map<String, Object> param) throws Exception {
        SoapObject request = new SoapObject(nanmespace, name);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            request.addProperty(entry.getKey(), entry.getValue());
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.bodyOut = request;
        HttpTransportSE ht = new HttpTransportSE(Constants.WEBSERVER_URL);
        ht.call(nanmespace + name, envelope);
        SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
        return soapPrimitive.toString();
    }

    ;

    public static String info(String name, Map<String, Object> param) throws Exception {
        SoapObject request = new SoapObject(Constants.WEBSERVER_NAMESPACE, name);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            request.addProperty(entry.getKey(), entry.getValue());
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.bodyOut = request;
        MyHttpTransportSE ht = new MyHttpTransportSE(Constants.WEBSERVER_URL);
        ht.call(Constants.WEBSERVER_NAMESPACE + name, envelope);
        SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
        return soapPrimitive.toString();
    }

}
