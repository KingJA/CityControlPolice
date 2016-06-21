package com.tdr.citycontrolpolice.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/20 13:36
 * 修改备注：
 */
@Table(name = "OCR_Kj")
public class OCR_Kj {
    @Column(name = "IDENTITYCARD", isId = true)
    private String IDENTITYCARD;
    @Column(name = "IDENTITYCARDID")
    private String IDENTITYCARDID;
    @Column(name = "TaskID")
    private String TaskID;
    @Column(name = "NAME")
    private String NAME;
    @Column(name = "SEX")
    private String SEX;
    @Column(name = "NATION")
    private String NATION;
    @Column(name = "BIRTHDAY")
    private String BIRTHDAY;
    @Column(name = "ADDRESS")
    private String ADDRESS;



    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getNATION() {
        return NATION;
    }

    public void setNATION(String NATION) {
        this.NATION = NATION;
    }

    public String getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getIDENTITYCARD() {
        return IDENTITYCARD;
    }

    public void setIDENTITYCARD(String IDENTITYCARD) {
        this.IDENTITYCARD = IDENTITYCARD;
    }

    public String getIDENTITYCARDID() {
        return IDENTITYCARDID;
    }

    public void setIDENTITYCARDID(String IDENTITYCARDID) {
        this.IDENTITYCARDID = IDENTITYCARDID;
    }

    @Override
    public String toString() {
        return "OCR_Kj{" +
                "IDENTITYCARDID='" + IDENTITYCARDID + '\'' +
                ", TaskID='" + TaskID + '\'' +
                ", NAME='" + NAME + '\'' +
                ", SEX='" + SEX + '\'' +
                ", NATION='" + NATION + '\'' +
                ", BIRTHDAY='" + BIRTHDAY + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", IDENTITYCARD='" + IDENTITYCARD + '\'' +
                '}';
    }
}
