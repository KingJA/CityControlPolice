package com.tdr.citycontrolpolice.entity;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：登录
 * 创建人：KingJA
 * 创建时间：2016/3/28 14:58
 * 修改备注：
 */
@Table(name = "SQL_Query")
public class SQL_Query {
    @Column(name = "keyWord", isId = true)
    private String keyWord;
    @Column(name = "date")
    private long date;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
