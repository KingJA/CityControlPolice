package com.tdr.citycontrolpolice.dao;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 13:49
 * 修改备注：
 */
public interface DbDao<T> {
    public T sleectFirst(Class<T> clazz, String key, String value);

    public T sleectFirst(Class<T> clazz, String key1, String value1, String key2, String value2);

    public List<T> selectAllWhere(Class<T> clazz, String key, String value);

    <T> List<T> selectAllWhereLike(Class<T> clazz, String key, String value);

    public List<T> selectAll(Class<T> clazz);

    public List<T> selectAllAndOrder(Class<T> clazz, String order);

    public void saveOrUpdate(T t);

    public void deleteById(Class<T> clazz, String id);

    public void deleteAll(Class<T> clazz);


}
