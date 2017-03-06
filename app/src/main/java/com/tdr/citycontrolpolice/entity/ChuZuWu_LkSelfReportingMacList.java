package com.tdr.citycontrolpolice.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/12/16 13:46
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_LkSelfReportingMacList {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_LkSelfReporingMacList
     * TaskID :  1
     * Content : [{"HOUSEID":"XXX","HOUSENAME":"XXX","ADDRESS":"XXX","MAC":"XXX","PHONE":"XXX"},{"HOUSEID":"XXX","HOUSENAME":"XXX","ADDRESS":"XXX","MAC":"XXX","PHONE":"XXX"}]
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * HOUSEID : XXX
     * HOUSENAME : XXX
     * ADDRESS : XXX
     * MAC : XXX
     * PHONE : XXX
     */

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

    public static class ContentBean implements Serializable {
        private String HOUSEID;
        private String HOUSENAME;

        public String getCHINESENAME() {
            return CHINESENAME;
        }

        public void setCHINESENAME(String CHINESENAME) {
            this.CHINESENAME = CHINESENAME;
        }

        private String CHINESENAME;
        private String ADDRESS;
        private String MAC;
        private String PHONE;

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

        public String getMAC() {
            return MAC;
        }

        public void setMAC(String MAC) {
            this.MAC = MAC;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

    }
}
