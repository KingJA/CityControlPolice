package com.tdr.citycontrolpolice.entity;

/**
 * Created by Administrator on 2016/3/7.
 */
public class Administrator {
    private String IDENTITYCARD;
    private String NAME;
    private String PHONE;

    public String getNEWUSERID() {
        return NEWUSERID;
    }

    public void setNEWUSERID(String NEWUSERID) {
        this.NEWUSERID = NEWUSERID;
    }

    private String NEWUSERID;

    public String getIDENTITYCARD() {
        return IDENTITYCARD;
    }

    public void setIDENTITYCARD(String IDENTITYCARD) {
        this.IDENTITYCARD = IDENTITYCARD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }
}
