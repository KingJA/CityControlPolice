package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/10/17 10:49
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_AdminList {


    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_AdminList
     * TaskID :  1
     * Content : {"HOUSEID":"XXX","AdminList":[{"IDENTITYCARD":"XXXXXXXXXX","NAME":"张三","SOURCE":"0"},{"IDENTITYCARD":"XXXXXXXXXX","NAME":"李四","SOURCE":"0"}]}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * HOUSEID : XXX
     * AdminList : [{"IDENTITYCARD":"XXXXXXXXXX","NAME":"张三","SOURCE":"0"},{"IDENTITYCARD":"XXXXXXXXXX","NAME":"李四","SOURCE":"0"}]
     */

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
        private String HOUSEID;
        /**
         * IDENTITYCARD : XXXXXXXXXX
         * NAME : 张三
         * SOURCE : 0
         */

        private List<AdminListBean> AdminList;

        public String getHOUSEID() {
            return HOUSEID;
        }

        public void setHOUSEID(String HOUSEID) {
            this.HOUSEID = HOUSEID;
        }

        public List<AdminListBean> getAdminList() {
            return AdminList;
        }

        public void setAdminList(List<AdminListBean> AdminList) {
            this.AdminList = AdminList;
        }

        public static class AdminListBean {
            private String IDENTITYCARD;
            private String NAME;
            private int SOURCE;
            private int ADMINTYPE;

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

            public int getSOURCE() {
                return SOURCE;
            }

            public void setSOURCE(int SOURCE) {
                this.SOURCE = SOURCE;
            }

            public int getADMINTYPE() {
                return ADMINTYPE;
            }

            public void setADMINTYPE(int ADMINTYPE) {
                this.ADMINTYPE = ADMINTYPE;
            }
        }
    }
}
