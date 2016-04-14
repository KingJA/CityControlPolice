package com.tdr.citycontrolpolice.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/2/26.
 */
@Table(name = "Basic_JuWeiHui")
public class Basic_JuWeiHui {
    @Id(column = "id")
    private int id;
    private String DMZM;
    private String DMMC;
    private String FDMZM;

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

    public String getFDMZM() {
        return FDMZM;
    }

    public void setFDMZM(String FDMZM) {
        this.FDMZM = FDMZM;
    }
}
