package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2017/3/6 10:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_DeviceInOutList {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_DeviceInOutList
     * TaskID :  1
     * Content : {"HOUSEID":"0123456789ABCDEF0123456789ABCDEF","PERSONNELINFOLIST":[{"ROOMID":"0123456789ABCDEF0123456789ABCDEF","DEVICEID":"0123456789ABCDEF0123456789ABCDEF ","MAXHEIGHT":"170","PEOPLENUMBER":"1","INOROUT":"19","SHOCKRANGE ":"1","MAXSHOCKTIME ":"19","PASSTIME ":"19"}]}
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * HOUSEID : 0123456789ABCDEF0123456789ABCDEF
     * PERSONNELINFOLIST : [{"ROOMID":"0123456789ABCDEF0123456789ABCDEF","DEVICEID":"0123456789ABCDEF0123456789ABCDEF ","MAXHEIGHT":"170","PEOPLENUMBER":"1","INOROUT":"19","SHOCKRANGE ":"1","MAXSHOCKTIME ":"19","PASSTIME ":"19"}]
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
         * MAXHEIGHT : 170
         * PEOPLENUMBER : 1
         * INOROUT : 19
         * SHOCKRANGE  : 1
         * MAXSHOCKTIME  : 19
         * PASSTIME  : 19
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
            private String DEVICECODE;
            private String MAXHEIGHT;
            private String PEOPLENUMBER;
            private String INOROUT;
            private String SHOCKRANGE;
            private String MAXSHOCKTIME;
            private String PASSTIME;

            public String getDEVICECODE() {
                return DEVICECODE;
            }

            public void setDEVICECODE(String DEVICECODE) {
                this.DEVICECODE = DEVICECODE;
            }

            public String getDEVICETIME() {
                return DEVICETIME;
            }

            public void setDEVICETIME(String DEVICETIME) {
                this.DEVICETIME = DEVICETIME;
            }

            private String DEVICETIME;

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

            public String getMAXHEIGHT() {
                return MAXHEIGHT;
            }

            public void setMAXHEIGHT(String MAXHEIGHT) {
                this.MAXHEIGHT = MAXHEIGHT;
            }

            public String getPEOPLENUMBER() {
                return PEOPLENUMBER;
            }

            public void setPEOPLENUMBER(String PEOPLENUMBER) {
                this.PEOPLENUMBER = PEOPLENUMBER;
            }

            public String getINOROUT() {
                return INOROUT;
            }

            public void setINOROUT(String INOROUT) {
                this.INOROUT = INOROUT;
            }

            public String getSHOCKRANGE() {
                return SHOCKRANGE;
            }

            public void setSHOCKRANGE(String SHOCKRANGE) {
                this.SHOCKRANGE = SHOCKRANGE;
            }

            public String getMAXSHOCKTIME() {
                return MAXSHOCKTIME;
            }

            public void setMAXSHOCKTIME(String MAXSHOCKTIME) {
                this.MAXSHOCKTIME = MAXSHOCKTIME;
            }

            public String getPASSTIME() {
                return PASSTIME;
            }

            public void setPASSTIME(String PASSTIME) {
                this.PASSTIME = PASSTIME;
            }
        }
    }
}
