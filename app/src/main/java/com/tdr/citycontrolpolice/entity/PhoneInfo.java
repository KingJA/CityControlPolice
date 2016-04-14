package com.tdr.citycontrolpolice.entity;

/**
 * Created by Administrator on 2016/2/26.
 */
public class PhoneInfo {
    private String SYSTEMTYPE;
    private String SYSTEMVERSION;
    private String DEVICEMODEL;
    private String IMSI;
    private String IMEI;
    private String ICCID;
    private String WIFIMAC;
    private String BTMAC;

    public String getSYSTEMTYPE() {
        return SYSTEMTYPE;
    }

    public void setSYSTEMTYPE(String SYSTEMTYPE) {
        this.SYSTEMTYPE = SYSTEMTYPE;
    }

    public String getSYSTEMVERSION() {
        return SYSTEMVERSION;
    }

    public void setSYSTEMVERSION(String SYSTEMVERSION) {
        this.SYSTEMVERSION = SYSTEMVERSION;
    }

    public String getDEVICEMODEL() {
        return DEVICEMODEL;
    }

    public void setDEVICEMODEL(String DEVICEMODEL) {
        this.DEVICEMODEL = DEVICEMODEL;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getWIFIMAC() {
        return WIFIMAC;
    }

    public void setWIFIMAC(String WIFIMAC) {
        this.WIFIMAC = WIFIMAC;
    }

    public String getBTMAC() {
        return BTMAC;
    }

    public void setBTMAC(String BTMAC) {
        this.BTMAC = BTMAC;
    }
}
