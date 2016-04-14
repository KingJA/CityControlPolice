package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/1 8:48
 * 修改备注：
 */
public class bbb extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbUtils db = DbUtils.create(this, "", "ct.db");
//        db.findAll(Selector.from(Basic_Dictionary.class).where("COLUMNCOMMENT","=","ssda"));

    }
}
