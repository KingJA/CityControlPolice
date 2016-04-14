package com.tdr.citycontrolpolice.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/2/26.
 */

@Table(name = "Basic_PaiChuSuo")
public class Basic_PaiChuSuo {
    @Id(column = "id")
    private int id;
    private String DMZM;
    private String DMMC;
    private String FDMZM;
    private String SANSHIYOUDMZM;


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

    public String getFDMZM() {
        return FDMZM;
    }

    public void setFDMZM(String FDMZM) {
        this.FDMZM = FDMZM;
    }

    public String getSANSHIYOUDMZM() {
        return SANSHIYOUDMZM;
    }

    public void setSANSHIYOUDMZM(String SANSHIYOUDMZM) {
        this.SANSHIYOUDMZM = SANSHIYOUDMZM;
    }
}
