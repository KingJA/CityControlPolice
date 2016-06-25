package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/25 13:19
 * 修改备注：
 */
public class Param_ChuZuWu_SetRoomInfoOfFavorites {


    private String TaskID;
    private String HOUSEID;
    private List<ContentBean> Content;

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

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private String ROOMID;
        private int REMIND_TYPE;
        private String TARGET;
        private String STARTDATE;
        private String ENDDATE;
        private String STARTTIME;
        private String ENDTIME;

        public String getROOMID() {
            return ROOMID;
        }

        public void setROOMID(String ROOMID) {
            this.ROOMID = ROOMID;
        }

        public int getREMIND_TYPE() {
            return REMIND_TYPE;
        }

        public void setREMIND_TYPE(int REMIND_TYPE) {
            this.REMIND_TYPE = REMIND_TYPE;
        }

        public String getTARGET() {
            return TARGET;
        }

        public void setTARGET(String TARGET) {
            this.TARGET = TARGET;
        }

        public String getSTARTDATE() {
            return STARTDATE;
        }

        public void setSTARTDATE(String STARTDATE) {
            this.STARTDATE = STARTDATE;
        }

        public String getENDDATE() {
            return ENDDATE;
        }

        public void setENDDATE(String ENDDATE) {
            this.ENDDATE = ENDDATE;
        }

        public String getSTARTTIME() {
            return STARTTIME;
        }

        public void setSTARTTIME(String STARTTIME) {
            this.STARTTIME = STARTTIME;
        }

        public String getENDTIME() {
            return ENDTIME;
        }

        public void setENDTIME(String ENDTIME) {
            this.ENDTIME = ENDTIME;
        }
    }
}
