package com.tdr.citycontrolpolice.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/2/26.
 */
@Table(name = "Basic_XingZhengQuHua")
public class Basic_XingZhengQuHua {
    @Id(column = "id")
    private int id;
    private String DMZM;
    private String DMMC;
    private String SORT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDMZM() {
        return DMZM;
    }

    public void setDMZM(String DMZM) {
        this.DMZM = DMZM;
    }

    public String getDMMC() {
        return DMMC;
    }

    public void setDMMC(String DMMC) {
        this.DMMC = DMMC;
    }

    public String getSORT() {
        return SORT;
    }

    public void setSORT(String SORT) {
        this.SORT = SORT;
    }
}
