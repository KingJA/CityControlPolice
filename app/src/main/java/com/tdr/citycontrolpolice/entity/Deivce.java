package com.tdr.citycontrolpolice.entity;

/**
 * Created by Administrator on 2016/2/29.
 */
public class Deivce {
    private String DEVICEID;
    private String DEVICETYPE;
    private String DEVICECODE;
    private String ROOMID;
    private String DEVICENAME;

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }

    public String getDEVICETYPE() {
        return DEVICETYPE;
    }

    public void setDEVICETYPE(String DEVICETYPE) {
        this.DEVICETYPE = DEVICETYPE;
    }

    public String getDEVICECODE() {
        return DEVICECODE;
    }

    public void setDEVICECODE(String DEVICECODE) {
        this.DEVICECODE = DEVICECODE;
    }

    public String getDEVICENAME() {
        return DEVICENAME;
    }

    public void setDEVICENAME(String DEVICENAME) {
        this.DEVICENAME = DEVICENAME;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }
}
