package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public class AddRoom {
    private String TaskID;
    private String HOUSEID;
    private String ROOMID;
    private String ROOMNO;
    private String FIXTURE;
    private String SQUARE;
    private int PRICE;
    private String SHI;
    private String TING;
    private String WEI;
    private String YANGTAI;
    private String GALLERYFUL;
    private String DEPOSIT;
    private int ISAUTOPUBLISH;
    private String TITLE;
    private int DEVICECOUNT;
    private int PHOTOCOUNT;
    private List<Deivce> DEVICE;
    private List<Photo> PHOTO;

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

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public String getROOMNO() {
        return ROOMNO;
    }

    public void setROOMNO(String ROOMNO) {
        this.ROOMNO = ROOMNO;
    }

    public String getFIXTURE() {
        return FIXTURE;
    }

    public void setFIXTURE(String FIXTURE) {
        this.FIXTURE = FIXTURE;
    }

    public String getSQUARE() {
        return SQUARE;
    }

    public void setSQUARE(String SQUARE) {
        this.SQUARE = SQUARE;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public String getSHI() {
        return SHI;
    }

    public void setSHI(String SHI) {
        this.SHI = SHI;
    }

    public String getTING() {
        return TING;
    }

    public void setTING(String TING) {
        this.TING = TING;
    }

    public String getWEI() {
        return WEI;
    }

    public void setWEI(String WEI) {
        this.WEI = WEI;
    }

    public String getYANGTAI() {
        return YANGTAI;
    }

    public void setYANGTAI(String YANGTAI) {
        this.YANGTAI = YANGTAI;
    }

    public String getGALLERYFUL() {
        return GALLERYFUL;
    }

    public void setGALLERYFUL(String GALLERYFUL) {
        this.GALLERYFUL = GALLERYFUL;
    }

    public String getDEPOSIT() {
        return DEPOSIT;
    }

    public void setDEPOSIT(String DEPOSIT) {
        this.DEPOSIT = DEPOSIT;
    }

    public int getISAUTOPUBLISH() {
        return ISAUTOPUBLISH;
    }

    public void setISAUTOPUBLISH(int ISAUTOPUBLISH) {
        this.ISAUTOPUBLISH = ISAUTOPUBLISH;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public int getDEVICECOUNT() {
        return DEVICECOUNT;
    }

    public void setDEVICECOUNT(int DEVICECOUNT) {
        this.DEVICECOUNT = DEVICECOUNT;
    }

    public int getPHOTOCOUNT() {
        return PHOTOCOUNT;
    }

    public void setPHOTOCOUNT(int PHOTOCOUNT) {
        this.PHOTOCOUNT = PHOTOCOUNT;
    }

    public List<Deivce> getDEVICE() {
        return DEVICE;
    }

    public void setDEVICE(List<Deivce> DEVICE) {
        this.DEVICE = DEVICE;
    }

    public List<Photo> getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(List<Photo> PHOTO) {
        this.PHOTO = PHOTO;
    }
}
