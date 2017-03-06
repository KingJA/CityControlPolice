package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/12/16 14:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CHUZUWU_ROOMLKSELFREPORTINGLIST {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : ChuZuWu_LKSelfReportingList
     * TaskID :  1
     * Content : [{"ROOMID":"0123456789ABCDEF0123456789ABCDEF","ROOMNO":"101","PERSONNELINFOLIST":[{"LISTID":"0123456789ABCDEF0123456789ABCDEF","NAME":"张三","IDENTITYCARD":"330303199909091234","PHONENUM":"13805771234","ROOMID":"0123456789ABCDEF0123456789ABCDEF","INTIME":"2016-4-13 10:13:23","MACLIST":"XXXXXXXX,XXXXXXXXXX,XXXXXXXXX"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","NAME":"黄荣智","IDENTITYCARD":"330381198306071811","PHONENUM":"18857758345","ROOMID":"0123456789ABCDEF0123456789ABCDEF","INTIME":"2016-4-13 10:13:23","MACLIST":"XXXXXXXX,XXXXXXXXXX,XXXXXXXXX"}]},{"ROOMID":"0123456789ABCDEF0123456789ABCDEF","ROOMNO":"101","PERSONNELINFOLIST":[{"LISTID":"0123456789ABCDEF0123456789ABCDEF","NAME":"张三","IDENTITYCARD":"330303199909091234","PHONENUM":"13805771234","ROOMID":"0123456789ABCDEF0123456789ABCDEF","INTIME":"2016-4-13 10:13:23","MACLIST":"XXXXXXXX,XXXXXXXXXX,XXXXXXXXX"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","NAME":"黄荣智","IDENTITYCARD":"330381198306071811","PHONENUM":"18857758345","ROOMID":"0123456789ABCDEF0123456789ABCDEF","INTIME":"2016-4-13 10:13:23","MACLIST":"XXXXXXXX,XXXXXXXXXX,XXXXXXXXX"}]}]
     */

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * ROOMID : 0123456789ABCDEF0123456789ABCDEF
     * ROOMNO : 101
     * PERSONNELINFOLIST : [{"LISTID":"0123456789ABCDEF0123456789ABCDEF","NAME":"张三","IDENTITYCARD":"330303199909091234","PHONENUM":"13805771234","ROOMID":"0123456789ABCDEF0123456789ABCDEF","INTIME":"2016-4-13 10:13:23","MACLIST":"XXXXXXXX,XXXXXXXXXX,XXXXXXXXX"},{"LISTID":"0123456789ABCDEF0123456789ABCDE0","NAME":"黄荣智","IDENTITYCARD":"330381198306071811","PHONENUM":"18857758345","ROOMID":"0123456789ABCDEF0123456789ABCDEF","INTIME":"2016-4-13 10:13:23","MACLIST":"XXXXXXXX,XXXXXXXXXX,XXXXXXXXX"}]
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

    public static class ContentBean {
        public boolean isEXPLAND() {
            return EXPLAND;
        }

        public void setEXPLAND(boolean EXPLAND) {
            this.EXPLAND = EXPLAND;
        }

        private boolean EXPLAND;
        private String ROOMID;
        private String ROOMNO;
        /**
         * LISTID : 0123456789ABCDEF0123456789ABCDEF
         * NAME : 张三
         * IDENTITYCARD : 330303199909091234
         * PHONENUM : 13805771234
         * ROOMID : 0123456789ABCDEF0123456789ABCDEF
         * INTIME : 2016-4-13 10:13:23
         * MACLIST : XXXXXXXX,XXXXXXXXXX,XXXXXXXXX
         */

        private List<PERSONNELINFOLISTBean> PERSONNELINFOLIST;

        public String getROOMID() {
            return ROOMID;
        }

        public void setROOMID(String ROOMID) {
            this.ROOMID = ROOMID;
        }

        public String getROOMNO() {
            return ROOMNO;
        }

        public void setROOMNO(String ROOMNO) {
            this.ROOMNO = ROOMNO;
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
            private String IDENTITYCARD;
            private String PHONENUM;
            private String ROOMID;
            private String INTIME;
            private String MACLIST;

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

            public String getMACLIST() {
                return MACLIST;
            }

            public void setMACLIST(String MACLIST) {
                this.MACLIST = MACLIST;
            }
        }
    }
}
