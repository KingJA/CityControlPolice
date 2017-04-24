package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/30 18:48
 * 修改备注：
 */
public class ChuZuWu_LKSelfReportingList {

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
            private String LISTID;
            private String NAME;
            private String STATUS;
            private String IDENTITYCARD;
            private String PHONENUM;
            private String ROOMID;
            private String INTIME;
            private String MACLIST;
            private int HEIGHT ;
            private boolean explend;

            public String getLISTID() {
                return LISTID;
            }

            public void setLISTID(String LISTID) {
                this.LISTID = LISTID;
            }

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

            public String getINTIME() {
                return INTIME;
            }

            public void setINTIME(String INTIME) {
                this.INTIME = INTIME;
            }

            public boolean isExplend() {
                return explend;
            }

            public void setExplend(boolean explend) {
                this.explend = explend;
            }

            public String getSTATUS() {
                return STATUS;
            }

            public void setSTATUS(String STATUS) {
                this.STATUS = STATUS;
            }

            public String getMACLIST() {
                return MACLIST;
            }

            public void setMACLIST(String MACLIST) {
                this.MACLIST = MACLIST;
            }

            public int getHEIGHT() {
                return HEIGHT;
            }

            public void setHEIGHT(int HEIGHT) {
                this.HEIGHT = HEIGHT;
            }
        }
    }
}
