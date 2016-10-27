package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
public class ChuZuWu_Add {
    private String TaskID;
    private String HOUSEID;
    private String HOUSENAME;
    private int HouseProperty;
    private String STANDARDADDRCODE;
    private String ADDRESS;
    private String HOUSINGESTATE;
    private String IDENTITYCARD;
    private String OWNERNAME;

    public String getOWNERNEWUSERID() {
        return OWNERNEWUSERID;
    }

    public void setOWNERNEWUSERID(String OWNERNEWUSERID) {
        this.OWNERNEWUSERID = OWNERNEWUSERID;
    }

    private String OWNERNEWUSERID;
    private String HOUSETYPE;
    private String PHONE;
    private String XQCODE;
    private String PCSCO;
    private String JWHCODE;
    private double LNG;
    private double LAT;
    private String QRCODE;
    private String ZRQ;
    private String RoomCount;
    private List<Room> RoomList;
    private String ADMINISTRATORCOUNT;
    private List<Administrator> ADMINISTRATOR;
    private String DEVICECOUNT;
    private List<Deivce> DEVICELIST;
    private String PHOTOCOUNT;

    public String getPHONELIST() {
        return PHONELIST;
    }

    public void setPHONELIST(String PHONELIST) {
        this.PHONELIST = PHONELIST;
    }

    private String PHONELIST;
    private List<Photo> PHOTOLIST;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
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

    public String getRoomCount() {
        return RoomCount;
    }

    public void setRoomCount(String roomCount) {
        RoomCount = roomCount;
    }

    public List<Room> getRoomList() {
        return RoomList;
    }

    public void setRoomList(List<Room> roomList) {
        RoomList = roomList;
    }

    public String getADMINISTRATORCOUNT() {
        return ADMINISTRATORCOUNT;
    }

    public void setADMINISTRATORCOUNT(String ADMINISTRATORCOUNT) {
        this.ADMINISTRATORCOUNT = ADMINISTRATORCOUNT;
    }


    public List<Administrator> getADMINISTRATOR() {
        return ADMINISTRATOR;
    }

    public void setADMINISTRATOR(List<Administrator> ADMINISTRATOR) {
        this.ADMINISTRATOR = ADMINISTRATOR;
    }

    public String getDEVICECOUNT() {
        return DEVICECOUNT;
    }

    public void setDEVICECOUNT(String DEVICECOUNT) {
        this.DEVICECOUNT = DEVICECOUNT;
    }

    public List<Deivce> getDEVICELIST() {
        return DEVICELIST;
    }

    public void setDEVICELIST(List<Deivce> DEVICELIST) {
        this.DEVICELIST = DEVICELIST;
    }

    public String getPHOTOCOUNT() {
        return PHOTOCOUNT;
    }

    public void setPHOTOCOUNT(String PHOTOCOUNT) {
        this.PHOTOCOUNT = PHOTOCOUNT;
    }

    public List<Photo> getPHOTOLIST() {
        return PHOTOLIST;
    }

    public void setPHOTOLIST(List<Photo> PHOTOLIST) {
        this.PHOTOLIST = PHOTOLIST;
    }

    public String getHOUSETYPE() {
        return HOUSETYPE;
    }

    public void setHOUSETYPE(String HOUSETYPE) {
        this.HOUSETYPE = HOUSETYPE;
    }

    public String getQRCODE() {
        return QRCODE;
    }

    public void setQRCODE(String QRCODE) {
        this.QRCODE = QRCODE;
    }

    public String getZRQ() {
        return ZRQ;
    }

    public void setZRQ(String ZRQ) {
        this.ZRQ = ZRQ;
    }

    public int getHouseProperty() {
        return HouseProperty;
    }

    public void setHouseProperty(int houseProperty) {
        HouseProperty = houseProperty;
    }
}
