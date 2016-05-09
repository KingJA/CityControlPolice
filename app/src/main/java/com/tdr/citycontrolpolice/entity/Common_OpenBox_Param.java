package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/9 9:22
 * 修改备注：
 */
public class Common_OpenBox_Param {


    private String TaskID;
    private String BOXID;
    private int DEVICETYPE;
    private int PHOTOCOUNT;
    private List<PHOTOLISTBean> PHOTOLIST;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getBOXID() {
        return BOXID;
    }

    public void setBOXID(String BOXID) {
        this.BOXID = BOXID;
    }

    public int getDEVICETYPE() {
        return DEVICETYPE;
    }

    public void setDEVICETYPE(int DEVICETYPE) {
        this.DEVICETYPE = DEVICETYPE;
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
