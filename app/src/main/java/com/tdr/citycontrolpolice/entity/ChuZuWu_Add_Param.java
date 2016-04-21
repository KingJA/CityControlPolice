package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/20 16:44
 * 修改备注：
 */
public class ChuZuWu_Add_Param {



    private String TaskID;
    private String HOUSEID;
    private String HOUSENAME;
    private String STANDARDADDRCODE;
    private String ADDRESS;
    private String HOUSINGESTATE;
    private String IDENTITYCARD;
    private String OWNERNAME;
    private String OWNERNEWUSERID;
    private String PHONE;
    private String XQCODE;
    private String PCSCO;
    private String JWHCODE;
    private double LNG;
    private double LAT;
    private int RoomCount;
    private int ADMINISTRATORCOUNT;
    private int DEVICECOUNT;
    private int PHOTOCOUNT;
    private List<RoomListBean> RoomList;
    private List<ADMINISTRATORBean> ADMINISTRATOR;
    private List<DEVICELISTBean> DEVICELIST;
    private List<PHOTOLISTBean> PHOTOLIST;

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

    public String getOWNERNEWUSERID() {
        return OWNERNEWUSERID;
    }

    public void setOWNERNEWUSERID(String OWNERNEWUSERID) {
        this.OWNERNEWUSERID = OWNERNEWUSERID;
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

    public int getRoomCount() {
        return RoomCount;
    }

    public void setRoomCount(int RoomCount) {
        this.RoomCount = RoomCount;
    }

    public int getADMINISTRATORCOUNT() {
        return ADMINISTRATORCOUNT;
    }

    public void setADMINISTRATORCOUNT(int ADMINISTRATORCOUNT) {
        this.ADMINISTRATORCOUNT = ADMINISTRATORCOUNT;
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

    public List<RoomListBean> getRoomList() {
        return RoomList;
    }

    public void setRoomList(List<RoomListBean> RoomList) {
        this.RoomList = RoomList;
    }

    public List<ADMINISTRATORBean> getADMINISTRATOR() {
        return ADMINISTRATOR;
    }

    public void setADMINISTRATOR(List<ADMINISTRATORBean> ADMINISTRATOR) {
        this.ADMINISTRATOR = ADMINISTRATOR;
    }

    public List<DEVICELISTBean> getDEVICELIST() {
        return DEVICELIST;
    }

    public void setDEVICELIST(List<DEVICELISTBean> DEVICELIST) {
        this.DEVICELIST = DEVICELIST;
    }

    public List<PHOTOLISTBean> getPHOTOLIST() {
        return PHOTOLIST;
    }

    public void setPHOTOLIST(List<PHOTOLISTBean> PHOTOLIST) {
        this.PHOTOLIST = PHOTOLIST;
    }

    public static class RoomListBean {
        private String RoomID;
        private int RoomNo;

        public String getRoomID() {
            return RoomID;
        }

        public void setRoomID(String RoomID) {
            this.RoomID = RoomID;
        }

        public int getRoomNo() {
            return RoomNo;
        }

        public void setRoomNo(int RoomNo) {
            this.RoomNo = RoomNo;
        }
    }

    public static class ADMINISTRATORBean {
        private String NEWUSERID;
        private String IDENTITYCARD;
        private String NAME;
        private String PHONE;

        public String getNEWUSERID() {
            return NEWUSERID;
        }

        public void setNEWUSERID(String NEWUSERID) {
            this.NEWUSERID = NEWUSERID;
        }

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

    public static class DEVICELISTBean {
        private String DEVICEID;
        private int DEVICETYPE;
        private int DEVICECODE;
        private String DEVICENAME;
        private String ROOMID;

        public String getDEVICEID() {
            return DEVICEID;
        }

        public void setDEVICEID(String DEVICEID) {
            this.DEVICEID = DEVICEID;
        }

        public int getDEVICETYPE() {
            return DEVICETYPE;
        }

        public void setDEVICETYPE(int DEVICETYPE) {
            this.DEVICETYPE = DEVICETYPE;
        }

        public int getDEVICECODE() {
            return DEVICECODE;
        }

        public void setDEVICECODE(int DEVICECODE) {
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

    public static class PHOTOLISTBean {
        private String LISTID;
        private String TAG;
        private String IMAGE;

        public String getLISTID() {
            return LISTID;
        }

        public void setLISTID(String LISTID) {
            this.LISTID = LISTID;
        }

        public String getTAG() {
            return TAG;
        }

        public void setTAG(String TAG) {
            this.TAG = TAG;
        }

        public String getIMAGE() {
            return IMAGE;
        }

        public void setIMAGE(String IMAGE) {
            this.IMAGE = IMAGE;
        }
    }
}
