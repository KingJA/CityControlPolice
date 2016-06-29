package com.tdr.citycontrolpolice.entity;

import java.io.Serializable;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/20 11:28
 * 修改备注：
 */
public class ChuZuWu_GetSSYByStandAddressCode {


    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    private ContentBean Content;

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

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean implements Serializable {
        private String STANDARDADDRCODE;
        private String IDENTITYCARD;
        private String OWNERNAME;
        private String PHONE;
        private String JWHCODE;
        private String ZRQ;

        public String getSTANDARDADDRCODE() {
            return STANDARDADDRCODE;
        }

        public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
            this.STANDARDADDRCODE = STANDARDADDRCODE;
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

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public String getJWHCODE() {
            return JWHCODE;
        }

        public void setJWHCODE(String JWHCODE) {
            this.JWHCODE = JWHCODE;
        }

        public String getZRQ() {
            return ZRQ;
        }

        public void setZRQ(String ZRQ) {
            this.ZRQ = ZRQ;
        }
    }
}
