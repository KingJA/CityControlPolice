package com.tdr.citycontrolpolice.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/9/7 10:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RentBean implements Serializable {

    private String HOUSEID;
    private String ROOMID;
    private String HOUSENAME;
    private String ADDRESS;
    private String OWNERNAME;
    private String PHONE;
    private int ISREGISTER;
    private String STANDARDADDRCODE;
    private String XQCODE;
    private String PCSCODE;
    private String JWHCODE;
    private int ISFAVORITE;
    private String HAS;

    private List<RoomListBean> RoomList;

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

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
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

    public int getISREGISTER() {
        return ISREGISTER;
    }

    public void setISREGISTER(int ISREGISTER) {
        this.ISREGISTER = ISREGISTER;
    }

    public String getSTANDARDADDRCODE() {
        return STANDARDADDRCODE;
    }

    public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
        this.STANDARDADDRCODE = STANDARDADDRCODE;
    }

    public String getXQCODE() {
        return XQCODE;
    }

    public void setXQCODE(String XQCODE) {
        this.XQCODE = XQCODE;
    }

    public String getPCSCODE() {
        return PCSCODE;
    }

    public void setPCSCODE(String PCSCODE) {
        this.PCSCODE = PCSCODE;
    }

    public String getJWHCODE() {
        return JWHCODE;
    }

    public void setJWHCODE(String JWHCODE) {
        this.JWHCODE = JWHCODE;
    }

    public int getISFAVORITE() {
        return ISFAVORITE;
    }

    public void setISFAVORITE(int ISFAVORITE) {
        this.ISFAVORITE = ISFAVORITE;
    }

    public String getHAS() {
        return HAS;
    }

    public void setHAS(String HAS) {
        this.HAS = HAS;
    }

    public List<RoomListBean> getRoomList() {
        return RoomList;
    }

    public void setRoomList(List<RoomListBean> RoomList) {
        this.RoomList = RoomList;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public static class RoomListBean implements Serializable{
        private String ROOMID;
        private int ROOMNO;
        private int DEPLOYSTATUS;
        private int HEADCOUNT;
        private int SHOUQUANCOUNT;
        private String STATIONNO;
        private boolean isExpland;

        public String getROOMID() {
            return ROOMID;
        }

        public void setROOMID(String ROOMID) {
            this.ROOMID = ROOMID;
        }

        public int getROOMNO() {
            return ROOMNO;
        }

        public void setROOMNO(int ROOMNO) {
            this.ROOMNO = ROOMNO;
        }

        public int getDEPLOYSTATUS() {
            return DEPLOYSTATUS;
        }

        public void setDEPLOYSTATUS(int DEPLOYSTATUS) {
            this.DEPLOYSTATUS = DEPLOYSTATUS;
        }

        public int getHEADCOUNT() {
            return HEADCOUNT;
        }

        public void setHEADCOUNT(int HEADCOUNT) {
            this.HEADCOUNT = HEADCOUNT;
        }

        public int getSHOUQUANCOUNT() {
            return SHOUQUANCOUNT;
        }

        public void setSHOUQUANCOUNT(int SHOUQUANCOUNT) {
            this.SHOUQUANCOUNT = SHOUQUANCOUNT;
        }

        public String getSTATIONNO() {
            return STATIONNO;
        }

        public void setSTATIONNO(String STATIONNO) {
            this.STATIONNO = STATIONNO;
        }

        public boolean isExpland() {
            return isExpland;
        }

        public void setExpland(boolean expland) {
            isExpland = expland;
        }
    }
}
