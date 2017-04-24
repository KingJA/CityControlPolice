package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2017/3/6 13:40
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_DevicePeopleNumberChange {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_DevicePeopleNumberChange
     * TaskID :  1
     * Content : {"HOUSEID":"0123456789ABCDEF0123456789ABCDEF","PERSONNELINFOLIST":[{"ROOMID":"0123456789ABCDEF0123456789ABCDEF","DEVICEID":"0123456789ABCDEF0123456789ABCDEF ","CALCULATEDATE":" 2017-02-01 ","FORECASTCYCLE":"1","FORECASTNUMBER":"1","ALAMCODE":"1","CREDIBILITY":"3"}]}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * HOUSEID : 0123456789ABCDEF0123456789ABCDEF
     * PERSONNELINFOLIST : [{"ROOMID":"0123456789ABCDEF0123456789ABCDEF","DEVICEID":"0123456789ABCDEF0123456789ABCDEF ","CALCULATEDATE":" 2017-02-01 ","FORECASTCYCLE":"1","FORECASTNUMBER":"1","ALAMCODE":"1","CREDIBILITY":"3"}]
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
         * ROOMID : 0123456789ABCDEF0123456789ABCDEF
         * DEVICEID : 0123456789ABCDEF0123456789ABCDEF
         * CALCULATEDATE :  2017-02-01
         * FORECASTCYCLE : 1
         * FORECASTNUMBER : 1
         * ALAMCODE : 1
         * CREDIBILITY : 3
         */

        private List<PERSONNELINFOLISTBean> PERSONNELINFOLIST;

        public String getHOUSEID() {
            return HOUSEID;
        }

        public void setHOUSEID(String HOUSEID) {
            this.HOUSEID = HOUSEID;
        }

        public List<PERSONNELINFOLISTBean> getPERSONNELINFOLIST() {
            return PERSONNELINFOLIST;
        }

        public void setPERSONNELINFOLIST(List<PERSONNELINFOLISTBean> PERSONNELINFOLIST) {
            this.PERSONNELINFOLIST = PERSONNELINFOLIST;
        }

        public static class PERSONNELINFOLISTBean {
            private String ROOMID;
            private String DEVICEID;
            private String CALCULATEDATE;
            private String FORECASTCYCLE;
            private String FORECASTNUMBER;
            private String ALAMCODE;
            private String CREDIBILITY;

            public String getROOMID() {
                return ROOMID;
            }

            public void setROOMID(String ROOMID) {
                this.ROOMID = ROOMID;
            }

            public String getDEVICEID() {
                return DEVICEID;
            }

            public void setDEVICEID(String DEVICEID) {
                this.DEVICEID = DEVICEID;
            }

            public String getCALCULATEDATE() {
                return CALCULATEDATE;
            }

            public void setCALCULATEDATE(String CALCULATEDATE) {
                this.CALCULATEDATE = CALCULATEDATE;
            }

            public String getFORECASTCYCLE() {
                return FORECASTCYCLE;
            }

            public void setFORECASTCYCLE(String FORECASTCYCLE) {
                this.FORECASTCYCLE = FORECASTCYCLE;
            }

            public String getFORECASTNUMBER() {
                return FORECASTNUMBER;
            }

            public void setFORECASTNUMBER(String FORECASTNUMBER) {
                this.FORECASTNUMBER = FORECASTNUMBER;
            }

            public String getALAMCODE() {
                return ALAMCODE;
            }

            public void setALAMCODE(String ALAMCODE) {
                this.ALAMCODE = ALAMCODE;
            }

            public String getCREDIBILITY() {
                return CREDIBILITY;
            }

            public void setCREDIBILITY(String CREDIBILITY) {
                this.CREDIBILITY = CREDIBILITY;
            }
        }
    }
}
