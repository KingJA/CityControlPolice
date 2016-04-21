package com.tdr.citycontrolpolice.czfinit;

import com.tdr.citycontrolpolice.entity.ErrorResult;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/20 9:24
 * 修改备注：
 */
public class CzfInitCallback {
    public interface WebServiceCallBack {
        <T> void onSuccess(T t);
        void onErrorResult(ErrorResult errorResult);
    }
}
