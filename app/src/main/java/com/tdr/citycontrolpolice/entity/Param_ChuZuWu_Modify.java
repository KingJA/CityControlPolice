package com.tdr.citycontrolpolice.entity;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/30 19:34
 * 修改备注：
 */
public class Param_ChuZuWu_Modify {

    private String TaskID;


    public int getHOUSEPROPERTY() {
        return HOUSEPROPERTY;
    }

    public void setHOUSEPROPERTY(int HOUSEPROPERTY) {
        this.HOUSEPROPERTY = HOUSEPROPERTY;
    }

    private int HOUSEPROPERTY;
    private String HOUSEID;
    private String HOUSENAME;
    private String STANDARDADDRCODE;
    private String ADDRESS;
    private String HOUSINGESTATE;
    private String IDENTITYCARD;
    private String OWNERNAME;
    private String PHONE;

    public String getPHONELIST() {
        return PHONELIST;
    }

    public void setPHONELIST(String PHONELIST) {
        this.PHONELIST = PHONELIST;
    }

    private String PHONELIST;
    private String XQCODE;
    private String PCSCO;
    private String JWHCODE;
    private double LNG;
    private double LAT;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getHOUSEID() {
        return HOUSEID;
    }

    public void setHOUSEID(String HOUSEID) {
        this.HOUSEID = HOUSEID;
    }

    public String getHOUSENAME() {
        return HOUSENAME;
    }

    public void setHOUSENAME(String HOUSENAME) {
        this.HOUSENAME = HOUSENAME;
    }

    public String getSTANDARDADDRCODE() {
        return STANDARDADDRCODE;
    }

    public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
        this.STANDARDADDRCODE = STANDARDADDRCODE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getHOUSINGESTATE() {
        return HOUSINGESTATE;
    }

    public void setHOUSINGESTATE(String HOUSINGESTATE) {
        this.HOUSINGESTATE = HOUSINGESTATE;
    }

    public String getIDENTITYCARD() {
        return IDENTITYCARD;
    }

    public void setIDENTITYCARD(String IDENTITYCARD) {
        this.IDENTITYCARD = IDENTITYCARD;
    }

    public String getOWNERNAME() {
        return OWNERNAME;
    }

    public void setOWNERNAME(String OWNERNAME) {
        this.OWNERNAME = OWNERNAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getXQCODE() {
        return XQCODE;
    }

    public void setXQCODE(String XQCODE) {
        this.XQCODE = XQCODE;
    }

    public String getPCSCO() {
        return PCSCO;
    }

    public void setPCSCO(String PCSCO) {
        this.PCSCO = PCSCO;
    }

    public String getJWHCODE() {
        return JWHCODE;
    }

    public void setJWHCODE(String JWHCODE) {
        this.JWHCODE = JWHCODE;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double LNG) {
        this.LNG = LNG;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double LAT) {
        this.LAT = LAT;
    }
}
