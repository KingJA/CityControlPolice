package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */
public class ChuZuWuInfo {
    private String HOUSEID;
    private String HOUSENAME;
    private boolean DianZiMenPai;
    private String OWNERNAME;
    private String PHONE;
    private String ADDRESS;
    private List<Room> RoomList;
    private List<PersonneliInfo> PersonList;

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

    public boolean isDianZiMenPai() {
        return DianZiMenPai;
    }

    public void setDianZiMenPai(boolean dianZiMenPai) {
        DianZiMenPai = dianZiMenPai;
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

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public List<Room> getRoomList() {
        return RoomList;
    }

    public void setRoomList(List<Room> roomList) {
        RoomList = roomList;
    }

    public List<PersonneliInfo> getPersonList() {
        return PersonList;
    }

    public void setPersonList(List<PersonneliInfo> personList) {
        PersonList = personList;
    }
}
