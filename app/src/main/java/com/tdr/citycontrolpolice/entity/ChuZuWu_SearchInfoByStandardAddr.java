package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/13 15:02
 * 修改备注：
 */
public class ChuZuWu_SearchInfoByStandardAddr {


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
        private String HOUSENAME;
        private String DianZiMenPai;
        private String ADDRESS;
        private String OWNERNAME;
        private String PHONE;
        private int ISREGISTER;
        private String STANDARDADDRCODE;
        private String XQCODE;
        private String PCSCODE;
        private String JWHCODE;
        private List<RoomListBean> RoomList;

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

        public String getDianZiMenPai() {
            return DianZiMenPai;
        }

        public void setDianZiMenPai(String DianZiMenPai) {
            this.DianZiMenPai = DianZiMenPai;
        }

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getOWNERNAME() {
            return OWNERNAME;
        }

        public void setOWNERNAME(String OWNERNAME) {
            this.OWNERNAME = OWNERNAME;
        }

        public String getPHONE() {
            return PHONE;
        }

        public void setPHONE(String PHONE) {
            this.PHONE = PHONE;
        }

        public int getISREGISTER() {
            return ISREGISTER;
        }

        public void setISREGISTER(int ISREGISTER) {
            this.ISREGISTER = ISREGISTER;
        }

        public String getSTANDARDADDRCODE() {
            return STANDARDADDRCODE;
        }

        public void setSTANDARDADDRCODE(String STANDARDADDRCODE) {
            this.STANDARDADDRCODE = STANDARDADDRCODE;
        }

        public String getXQCODE() {
            return XQCODE;
        }

        public void setXQCODE(String XQCODE) {
            this.XQCODE = XQCODE;
        }

        public String getPCSCODE() {
            return PCSCODE;
        }

        public void setPCSCODE(String PCSCODE) {
            this.PCSCODE = PCSCODE;
        }

        public String getJWHCODE() {
            return JWHCODE;
        }

        public void setJWHCODE(String JWHCODE) {
            this.JWHCODE = JWHCODE;
        }

        public List<RoomListBean> getRoomList() {
            return RoomList;
        }

        public void setRoomList(List<RoomListBean> RoomList) {
            this.RoomList = RoomList;
        }

        public static class RoomListBean {
            private String ROOMID;
            private int ROOMNO;
            private int DEPLOYSTATUS;
            private int HEADCOUNT;
            private String STATIONNO;

            public String getROOMID() {
                return ROOMID;
            }

            public void setROOMID(String ROOMID) {
                this.ROOMID = ROOMID;
            }

            public int getROOMNO() {
                return ROOMNO;
            }

            public void setROOMNO(int ROOMNO) {
                this.ROOMNO = ROOMNO;
            }

            public int getDEPLOYSTATUS() {
                return DEPLOYSTATUS;
            }

            public void setDEPLOYSTATUS(int DEPLOYSTATUS) {
                this.DEPLOYSTATUS = DEPLOYSTATUS;
            }

            public int getHEADCOUNT() {
                return HEADCOUNT;
            }

            public void setHEADCOUNT(int HEADCOUNT) {
                this.HEADCOUNT = HEADCOUNT;
            }

            public String getSTATIONNO() {
                return STATIONNO;
            }

            public void setSTATIONNO(String STATIONNO) {
                this.STATIONNO = STATIONNO;
            }
        }
    }
}
