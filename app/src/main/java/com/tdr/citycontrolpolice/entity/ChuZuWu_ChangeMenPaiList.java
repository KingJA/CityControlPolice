package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/30 15:24
 * 修改备注：
 */
public class ChuZuWu_ChangeMenPaiList {


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
        private String RECORDID;
        private String DEVICEID;
        private String DEVICETYPE;
        private String OLDDEVICECODE;
        private String NEWDEVICECODE;
        private String RECORDTIME;
        private String OPERATOR;
        private String OPERATOR_NAME;
        private int REASON_TYPE;
        private String REASON;

        public String getRECORDID() {
            return RECORDID;
        }

        public void setRECORDID(String RECORDID) {
            this.RECORDID = RECORDID;
        }

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

        public String getOLDDEVICECODE() {
            return OLDDEVICECODE;
        }

        public void setOLDDEVICECODE(String OLDDEVICECODE) {
            this.OLDDEVICECODE = OLDDEVICECODE;
        }

        public String getNEWDEVICECODE() {
            return NEWDEVICECODE;
        }

        public void setNEWDEVICECODE(String NEWDEVICECODE) {
            this.NEWDEVICECODE = NEWDEVICECODE;
        }

        public String getRECORDTIME() {
            return RECORDTIME;
        }

        public void setRECORDTIME(String RECORDTIME) {
            this.RECORDTIME = RECORDTIME;
        }

        public String getOPERATOR() {
            return OPERATOR;
        }

        public void setOPERATOR(String OPERATOR) {
            this.OPERATOR = OPERATOR;
        }

        public String getOPERATOR_NAME() {
            return OPERATOR_NAME;
        }

        public void setOPERATOR_NAME(String OPERATOR_NAME) {
            this.OPERATOR_NAME = OPERATOR_NAME;
        }

        public int getREASON_TYPE() {
            return REASON_TYPE;
        }

        public void setREASON_TYPE(int REASON_TYPE) {
            this.REASON_TYPE = REASON_TYPE;
        }

        public String getREASON() {
            return REASON;
        }

        public void setREASON(String REASON) {
            this.REASON = REASON;
        }
    }
}
