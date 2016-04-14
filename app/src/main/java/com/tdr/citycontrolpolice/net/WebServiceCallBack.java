package com.tdr.citycontrolpolice.net;

import com.tdr.citycontrolpolice.entity.ErrorResult;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：WebService管理类
 * 创建人：KingJA
 * 创建时间：2016/3/22 13:08
 * 修改备注：
 */
public interface WebServiceCallBack<T> {
    void onSuccess(T t);

    void onErrorResult(ErrorResult errorResult);
}


