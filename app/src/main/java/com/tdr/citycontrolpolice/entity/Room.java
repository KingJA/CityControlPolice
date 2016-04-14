package com.tdr.citycontrolpolice.entity;

/**
 * Created by Administrator on 2016/3/7.
 */
public class Room {
    private String ROOMID;
    private String ROOMNO;
    private String DEPLOYSTATUS;


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

    public String getDEPLOYSTATUS() {
        return DEPLOYSTATUS;
    }

    public void setDEPLOYSTATUS(String DEPLOYSTATUS) {
        this.DEPLOYSTATUS = DEPLOYSTATUS;
    }
}
