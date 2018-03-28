package com.tdr.citycontrolpolice.entity;

/**
 * Description:TODO
 * Create Time:2017/12/22 10:37
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_LoginByPStore {

    /**
     * ResultCode : 0
     * ResultText : 登录成功
     * DataTypeCode : User_LoginByPStore
     * TaskID :  1
     * Content : {"TOKEN":"XXX","USERID":"XXX","NAME":"张三","PHONE":"13805771234","IDENTITYCARD":"330303199909091234"}
     */

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

    public static class ContentBean {
        /**
         * TOKEN : XXX
         * USERID : XXX
         * NAME : 张三
         * PHONE : 13805771234
         * IDENTITYCARD : 330303199909091234
         */

        private String TOKEN;
        private String USERID;
        private String NAME;
        private String PHONE;
        private String IDENTITYCARD;

        public String getTOKEN() {
            return TOKEN;
        }

        public void setTOKEN(String TOKEN) {
            this.TOKEN = TOKEN;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
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

        public String getIDENTITYCARD() {
            return IDENTITYCARD;
        }

        public void setIDENTITYCARD(String IDENTITYCARD) {
            this.IDENTITYCARD = IDENTITYCARD;
        }
    }
}
