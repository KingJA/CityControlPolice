package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/21 10:41
 * 修改备注：
 */
public class ChuZuWu_ComprehensiveInfo {


    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private int TaskID;
    private ContentBean Content;
    private String StableVersion;

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

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int TaskID) {
        this.TaskID = TaskID;
    }

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public String getStableVersion() {
        return StableVersion;
    }

    public void setStableVersion(String StableVersion) {
        this.StableVersion = StableVersion;
    }

    public static class ContentBean {
        private String HOUSEID;
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
            private String NAME;
            private String IDENTITYCARD;
            private String PHONENUM;
            private String ROOMID;
            private int ISXJ;
            private int ZZSB;
            private int LK;
            private int DEV;
            private int TF;
            private int QKRY;

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getIDENTITYCARD() {
                return IDENTITYCARD;
            }

            public void setIDENTITYCARD(String IDENTITYCARD) {
                this.IDENTITYCARD = IDENTITYCARD;
            }

            public String getPHONENUM() {
                return PHONENUM;
            }

            public void setPHONENUM(String PHONENUM) {
                this.PHONENUM = PHONENUM;
            }

            public String getROOMID() {
                return ROOMID;
            }

            public void setROOMID(String ROOMID) {
                this.ROOMID = ROOMID;
            }

            public int getISXJ() {
                return ISXJ;
            }

            public void setISXJ(int ISXJ) {
                this.ISXJ = ISXJ;
            }

            public int getZZSB() {
                return ZZSB;
            }

            public void setZZSB(int ZZSB) {
                this.ZZSB = ZZSB;
            }

            public int getLK() {
                return LK;
            }

            public void setLK(int LK) {
                this.LK = LK;
            }

            public int getDEV() {
                return DEV;
            }

            public void setDEV(int DEV) {
                this.DEV = DEV;
            }

            public int getTF() {
                return TF;
            }

            public void setTF(int TF) {
                this.TF = TF;
            }

            public int getQKRY() {
                return QKRY;
            }

            public void setQKRY(int QKRY) {
                this.QKRY = QKRY;
            }
        }
    }
}
