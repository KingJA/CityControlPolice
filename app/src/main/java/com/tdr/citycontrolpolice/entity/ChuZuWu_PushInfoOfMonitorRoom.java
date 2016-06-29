package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/27 10:49
 * 修改备注：
 */
public class ChuZuWu_PushInfoOfMonitorRoom {

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    private List<ContentBean> Content;

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getResultText() {
        return ResultText;
    }

    public void setResultText(String ResultText) {
        this.ResultText = ResultText;
    }

    public String getDataTypeCode() {
        return DataTypeCode;
    }

    public void setDataTypeCode(String DataTypeCode) {
        this.DataTypeCode = DataTypeCode;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private String LISTID;
        private String HOUSEID;
        private String ROOMID;
        private String TARGET;
        private int MESSAGETYPE;
        private String MESSAGE;
        private String PUSHTIME;
        private int PUSHTYPE;
        private String FAILREASON;
        private String INTIME;
        private String REMARK;
        private String ADDRESS;
        private String ROOMNO;
        private int REMIND_TYPE;

        public String getLISTID() {
            return LISTID;
        }

        public void setLISTID(String LISTID) {
            this.LISTID = LISTID;
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

        public String getTARGET() {
            return TARGET;
        }

        public void setTARGET(String TARGET) {
            this.TARGET = TARGET;
        }

        public int getMESSAGETYPE() {
            return MESSAGETYPE;
        }

        public void setMESSAGETYPE(int MESSAGETYPE) {
            this.MESSAGETYPE = MESSAGETYPE;
        }

        public String getMESSAGE() {
            return MESSAGE;
        }

        public void setMESSAGE(String MESSAGE) {
            this.MESSAGE = MESSAGE;
        }

        public String getPUSHTIME() {
            return PUSHTIME;
        }

        public void setPUSHTIME(String PUSHTIME) {
            this.PUSHTIME = PUSHTIME;
        }

        public int getPUSHTYPE() {
            return PUSHTYPE;
        }

        public void setPUSHTYPE(int PUSHTYPE) {
            this.PUSHTYPE = PUSHTYPE;
        }

        public String getFAILREASON() {
            return FAILREASON;
        }

        public void setFAILREASON(String FAILREASON) {
            this.FAILREASON = FAILREASON;
        }

        public String getINTIME() {
            return INTIME;
        }

        public void setINTIME(String INTIME) {
            this.INTIME = INTIME;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getROOMNO() {
            return ROOMNO;
        }

        public void setROOMNO(String ROOMNO) {
            this.ROOMNO = ROOMNO;
        }


        public int getREMIND_TYPE() {
            return REMIND_TYPE;
        }

        public void setREMIND_TYPE(int REMIND_TYPE) {
            this.REMIND_TYPE = REMIND_TYPE;
        }
    }
}
