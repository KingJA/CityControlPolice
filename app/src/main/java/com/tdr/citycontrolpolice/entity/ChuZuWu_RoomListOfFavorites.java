package com.tdr.citycontrolpolice.entity;

import java.util.List;

public class ChuZuWu_RoomListOfFavorites {


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
        private List<MonitorRoomListBean> MonitorRoomList;

        public String getHOUSEID() {
            return HOUSEID;
        }

        public void setHOUSEID(String HOUSEID) {
            this.HOUSEID = HOUSEID;
        }

        public List<MonitorRoomListBean> getMonitorRoomList() {
            return MonitorRoomList;
        }

        public void setMonitorRoomList(List<MonitorRoomListBean> MonitorRoomList) {
            this.MonitorRoomList = MonitorRoomList;
        }

        public static class MonitorRoomListBean {
            private String ROOMID;
            private int REMIND_TYPE;
            private String ROOMNO;
            private String TARGET;
            private String STARTDATE;
            private String ENDDATE;
            private String STARTTIME;
            private String ENDTIME;
            private boolean isChecked;

            @Override
            public String toString() {
                return "MonitorRoomListBean{" +
                        "ROOMID='" + ROOMID + '\'' +
                        ", REMIND_TYPE=" + REMIND_TYPE +
                        ", ROOMNO='" + ROOMNO + '\'' +
                        ", TARGET='" + TARGET + '\'' +
                        ", STARTDATE='" + STARTDATE + '\'' +
                        ", ENDDATE='" + ENDDATE + '\'' +
                        ", STARTTIME='" + STARTTIME + '\'' +
                        ", ENDTIME='" + ENDTIME + '\'' +
                        ", isChecked=" + isChecked +
                        '}';
            }

            public String getROOMID() {
                return ROOMID;
            }

            public void setROOMID(String ROOMID) {
                this.ROOMID = ROOMID;
            }

            public int getREMIND_TYPE() {
                return REMIND_TYPE;
            }

            public void setREMIND_TYPE(int REMIND_TYPE) {
                this.REMIND_TYPE = REMIND_TYPE;
            }

            public String getROOMNO() {
                return ROOMNO;
            }

            public void setROOMNO(String ROOMNO) {
                this.ROOMNO = ROOMNO;
            }

            public String getTARGET() {
                return TARGET;
            }

            public void setTARGET(String TARGET) {
                this.TARGET = TARGET;
            }

            public String getSTARTDATE() {
                return STARTDATE;
            }

            public void setSTARTDATE(String STARTDATE) {
                this.STARTDATE = STARTDATE;
            }

            public String getENDDATE() {
                return ENDDATE;
            }

            public void setENDDATE(String ENDDATE) {
                this.ENDDATE = ENDDATE;
            }

            public String getSTARTTIME() {
                return STARTTIME;
            }

            public void setSTARTTIME(String STARTTIME) {
                this.STARTTIME = STARTTIME;
            }

            public String getENDTIME() {
                return ENDTIME;
            }

            public void setENDTIME(String ENDTIME) {
                this.ENDTIME = ENDTIME;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }
        }
    }
}
