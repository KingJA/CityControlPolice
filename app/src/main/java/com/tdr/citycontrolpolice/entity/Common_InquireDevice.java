package com.tdr.citycontrolpolice.entity;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/7 14:26
 * 修改备注：
 */
public class Common_InquireDevice {

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
        private String DEVICEID;
        private String DEVICETYPE;
        private String DEVICECODE;
        private String DEVICENAME;
        private int OTHERTYPE;
        private String OTHERID;
        private String ROOMID;

        public String getDEVICEID() {
            return DEVICEID;
        }

        public void setDEVICEID(String DEVICEID) {
            this.DEVICEID = DEVICEID;
        }

        public String getDEVICETYPE() {
            return DEVICETYPE;
        }

        public void setDEVICETYPE(String DEVICETYPE) {
            this.DEVICETYPE = DEVICETYPE;
        }

        public String getDEVICECODE() {
            return DEVICECODE;
        }

        public void setDEVICECODE(String DEVICECODE) {
            this.DEVICECODE = DEVICECODE;
        }

        public String getDEVICENAME() {
            return DEVICENAME;
        }

        public void setDEVICENAME(String DEVICENAME) {
            this.DEVICENAME = DEVICENAME;
        }

        public int getOTHERTYPE() {
            return OTHERTYPE;
        }

        public void setOTHERTYPE(int OTHERTYPE) {
            this.OTHERTYPE = OTHERTYPE;
        }

        public String getOTHERID() {
            return OTHERID;
        }

        public void setOTHERID(String OTHERID) {
            this.OTHERID = OTHERID;
        }

        public String getROOMID() {
            return ROOMID;
        }

        public void setROOMID(String ROOMID) {
            this.ROOMID = ROOMID;
        }
    }
}
