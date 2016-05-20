package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/19 14:26
 * 修改备注：
 */
public class ChuZuWu_ActivecardChangeList {


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
            private String CARDID;
            private int CARDTYPE;
            private int CHANGE_STATUS;
            private String ROOMID;
            private String DEVICETIME;

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

            public String getCARDID() {
                return CARDID;
            }

            public void setCARDID(String CARDID) {
                this.CARDID = CARDID;
            }

            public int getCARDTYPE() {
                return CARDTYPE;
            }

            public void setCARDTYPE(int CARDTYPE) {
                this.CARDTYPE = CARDTYPE;
            }

            public int getCHANGE_STATUS() {
                return CHANGE_STATUS;
            }

            public void setCHANGE_STATUS(int CHANGE_STATUS) {
                this.CHANGE_STATUS = CHANGE_STATUS;
            }

            public String getROOMID() {
                return ROOMID;
            }

            public void setROOMID(String ROOMID) {
                this.ROOMID = ROOMID;
            }

            public String getDEVICETIME() {
                return DEVICETIME;
            }

            public void setDEVICETIME(String DEVICETIME) {
                this.DEVICETIME = DEVICETIME;
            }
        }
    }
}
