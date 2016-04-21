package com.tdr.citycontrolpolice.czfinit;

import android.app.Activity;

import com.tdr.citycontrolpolice.net.WebServiceCallBack;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/20 8:40
 * 修改备注：
 */
public interface CzfInitContract {

    interface View {
        void showProgress();

        void hideProgress();

        void showError();
    }

    interface Model {
        <T> void uploadData(Activity context, String token, String method, Object param, Class<T> clazz, WebServiceCallBack<T> callBack);

        <T> void searchAddress(Activity context, String token, String method, Object param, Class<T> clazz, WebServiceCallBack<T> callBack);
    }

    interface Presenter {
        <T> void uploadData(Activity context, String token, String method, Object param, Class<T> clazz);

        <T> void searchAddress(Activity context, String token, String method, Object param, Class<T> clazz);
    }


}
