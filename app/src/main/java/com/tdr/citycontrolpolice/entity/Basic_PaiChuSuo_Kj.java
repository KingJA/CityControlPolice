package com.tdr.citycontrolpolice.entity;



import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：登录
 * 创建人：KingJA
 * 创建时间：2016/3/28 14:58
 * 修改备注：
 */
@Table(name = "Basic_PaiChuSuo")
public class Basic_PaiChuSuo_Kj implements Serializable {
    @Column(name = "DMZM", isId = true)
    private String DMZM;
    @Column(name = "DMMC")
    private String DMMC;
    @Column(name = "FDMZM")
    private String FDMZM;
    @Column(name = "SANSHIYOUDMZM")
    private String SANSHIYOUDMZM;



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
