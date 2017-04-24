package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/18 14:48
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_LKSelfReportingInParam {

    /**
     * TaskID : 1
     * HOUSEID : 0123456789ABCDEF0123456789ABCDEF
     * ROOMID : 0123456789ABCDEF0123456789ABCDEF
     * LISTID : 0123456789ABCDEF0123456789ABCDEF
     * NAME : 张三
     * IDENTITYCARD : 330302198701234567
     * PHONE : 13805778888
     * REPORTERROLE : 1
     * OPERATOR : 0123456789ABCDEF0123456789ABCDEF
     * STANDARDADDRCODE : 3303022015885422563563
     * TERMINAL : 1
     * XQCODE : 330302
     * PCSCODE : 330302098000
     * JWHCODE : 330302008001
     * OPERATORPHONE : 13905771234
     * PHOTOCOUNT : 2
     * PHOTOLIST : [{"LISTID":"0123456789ABCDEF0123456789ABCDEF","TAG":"门","IMAGE":"base64"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","TAG":"设备","IMAGE":"base64"}]
     */

    private String TaskID;
    private String HOUSEID;
    private String ROOMID;
    private String LISTID;
    private String NAME;
    private String IDENTITYCARD;
    private String PHONE;
    private int REPORTERROLE;
    private String OPERATOR;
    private String STANDARDADDRCODE;
    private int TERMINAL;
    private int HEIGHT;
    private String XQCODE;
    private String PCSCODE;
    private String JWHCODE;
    private String OPERATORPHONE;
    private int PHOTOCOUNT;
    /**
     * LISTID : 0123456789ABCDEF0123456789ABCDEF
     * TAG : 门
     * IMAGE : base64
     */
    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }
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

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public String getLISTID() {
        return LISTID;
    }

    public void setLISTID(String LISTID) {
        this.LISTID = LISTID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getIDENTITYCARD() {
        return IDENTITYCARD;
    }

    public void setIDENTITYCARD(String IDENTITYCARD) {
        this.IDENTITYCARD = IDENTITYCARD;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public int getREPORTERROLE() {
        return REPORTERROLE;
    }

    public void setREPORTERROLE(int REPORTERROLE) {
        this.REPORTERROLE = REPORTERROLE;
    }

    public String getOPERATOR() {
        return OPERATOR;
    }

    public void setOPERATOR(String OPERATOR) {
        this.OPERATOR = OPERATOR;
    }

    public String getSTANDARDADDRCODE() {
        return STANDARDADDRCODE;
    }

    public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
        this.STANDARDADDRCODE = STANDARDADDRCODE;
    }

    public int getTERMINAL() {
        return TERMINAL;
    }

    public void setTERMINAL(int TERMINAL) {
        this.TERMINAL = TERMINAL;
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

    public String getOPERATORPHONE() {
        return OPERATORPHONE;
    }

    public void setOPERATORPHONE(String OPERATORPHONE) {
        this.OPERATORPHONE = OPERATORPHONE;
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
