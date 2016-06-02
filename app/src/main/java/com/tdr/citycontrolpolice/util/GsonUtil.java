package com.tdr.citycontrolpolice.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GsonUtil {


    public static Object getJsonObject(String json, Class<?> cls) {
        Gson gs = new Gson();
        return gs.fromJson(json, cls);
    }

    public static Object StringToObject(String str, Type type) {
        Object obj = null;
        Gson gs = new Gson();
        obj = gs.fromJson(str, type);

        return obj;
    }

    public static String ObjectToString(Object obj) {
        String rlt = null;
        Gson gs = new Gson();
        rlt = gs.toJson(obj);
        return rlt;
    }


}
