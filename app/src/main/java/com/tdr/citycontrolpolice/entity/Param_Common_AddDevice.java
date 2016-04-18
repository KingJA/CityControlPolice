package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/7 17:32
 * 修改备注：
 */
public class Param_Common_AddDevice {

    private String TaskID;
    private String DEVICEID;
    private int DEVICETYPE;
    private long DEVICECODE;
    private String DEVICENAME;
    private int OTHERTYPE;
    private String OTHERID;
    private String ROOMID;
    private int PHOTOCOUNT;
    private List<PHOTOLISTBean> PHOTOLIST;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

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

    public long getDEVICECODE() {
        return DEVICECODE;
    }

    public void setDEVICECODE(long DEVICECODE) {
        this.DEVICECODE = DEVICECODE;
    }

    public String getDEVICENAME() {
        return DEVICENAME;
    }

    public void setDEVICENAME(String DEVICENAME) {
        this.DEVICENAME = DEVICENAME;
    }

    public int getOTHERTYPE() {
        return OTHERTYPE;
    }

    public void setOTHERTYPE(int OTHERTYPE) {
        this.OTHERTYPE = OTHERTYPE;
    }

    public String getOTHERID() {
        return OTHERID;
    }

    public void setOTHERID(String OTHERID) {
        this.OTHERID = OTHERID;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public int getPHOTOCOUNT() {
        return PHOTOCOUNT;
    }

    public void setPHOTOCOUNT(int PHOTOCOUNT) {
        this.PHOTOCOUNT = PHOTOCOUNT;
    }

    public List<PHOTOLISTBean> getPHOTOLIST() {
        return PHOTOLIST;
    }

    public void setPHOTOLIST(List<PHOTOLISTBean> PHOTOLIST) {
        this.PHOTOLIST = PHOTOLIST;
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
