package com.tdr.citycontrolpolice.entity;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：流动人员详情
 * 创建人：KingJA
 * 创建时间：2016/4/13 15:48
 * 修改备注：
 */
public class Common_LKRenYuanXinxi {

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
        private String ZZZH;
        private String XM;
        private String SFZH;
        private String HKSX;
        private String HKXZ;
        private String BM;
        private String XB;
        private String CSRQ;
        private String MZ;
        private String HYZK;
        private String WHCD;
        private String DQFL;
        private String DJRQ;
        private String DQRQ;
        private String CSZY;
        private String GZCS;
        private String GZDZ;
        private String LXDH;

        public String getZZZH() {
            return ZZZH;
        }

        public void setZZZH(String ZZZH) {
            this.ZZZH = ZZZH;
        }

        public String getXM() {
            return XM;
        }

        public void setXM(String XM) {
            this.XM = XM;
        }

        public String getSFZH() {
            return SFZH;
        }

        public void setSFZH(String SFZH) {
            this.SFZH = SFZH;
        }

        public String getHKSX() {
            return HKSX;
        }

        public void setHKSX(String HKSX) {
            this.HKSX = HKSX;
        }

        public String getHKXZ() {
            return HKXZ;
        }

        public void setHKXZ(String HKXZ) {
            this.HKXZ = HKXZ;
        }

        public String getBM() {
            return BM;
        }

        public void setBM(String BM) {
            this.BM = BM;
        }

        public String getXB() {
            return XB;
        }

        public void setXB(String XB) {
            this.XB = XB;
        }

        public String getCSRQ() {
            return CSRQ;
        }

        public void setCSRQ(String CSRQ) {
            this.CSRQ = CSRQ;
        }

        public String getMZ() {
            return MZ;
        }

        public void setMZ(String MZ) {
            this.MZ = MZ;
        }

        public String getHYZK() {
            return HYZK;
        }

        public void setHYZK(String HYZK) {
            this.HYZK = HYZK;
        }

        public String getWHCD() {
            return WHCD;
        }

        public void setWHCD(String WHCD) {
            this.WHCD = WHCD;
        }

        public String getDQFL() {
            return DQFL;
        }

        public void setDQFL(String DQFL) {
            this.DQFL = DQFL;
        }

        public String getDJRQ() {
            return DJRQ;
        }

        public void setDJRQ(String DJRQ) {
            this.DJRQ = DJRQ;
        }

        public String getDQRQ() {
            return DQRQ;
        }

        public void setDQRQ(String DQRQ) {
            this.DQRQ = DQRQ;
        }

        public String getCSZY() {
            return CSZY;
        }

        public void setCSZY(String CSZY) {
            this.CSZY = CSZY;
        }

        public String getGZCS() {
            return GZCS;
        }

        public void setGZCS(String GZCS) {
            this.GZCS = GZCS;
        }

        public String getGZDZ() {
            return GZDZ;
        }

        public void setGZDZ(String GZDZ) {
            this.GZDZ = GZDZ;
        }

        public String getLXDH() {
            return LXDH;
        }

        public void setLXDH(String LXDH) {
            this.LXDH = LXDH;
        }
    }
}
