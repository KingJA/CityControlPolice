package com.tdr.citycontrolpolice.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/8.
 */
public class KjChuZuWuInfo implements Serializable {

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

    public static class ContentBean implements Serializable {
        private String HOUSEID;
        private String HOUSENAME;
        private int ISREGISTER;
        private boolean DianZiMenPai;
        private String OWNERNAME;
        private String PHONE;
        private String ADDRESS;
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

        public int getISREGISTER() {
            return ISREGISTER;
        }

        public void setISREGISTER(int ISREGISTER) {
            this.ISREGISTER = ISREGISTER;
        }

        public boolean isDianZiMenPai() {
            return DianZiMenPai;
        }

        public void setDianZiMenPai(boolean DianZiMenPai) {
            this.DianZiMenPai = DianZiMenPai;
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

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public List<RoomListBean> getRoomList() {
            return RoomList;
        }

        public void setRoomList(List<RoomListBean> RoomList) {
            this.RoomList = RoomList;
        }

        public static class RoomListBean implements Serializable {
            private String ROOMID;
            private String STATIONNO;
            private int ROOMNO;
            private int DEPLOYSTATUS;
            private int HEADCOUT;

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

            public int getHEADCOUT() {
                return HEADCOUT;
            }

            public void setHEADCOUT(int HEADCOUT) {
                this.HEADCOUT = HEADCOUT;
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
