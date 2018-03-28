package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/2/9 11:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ChuZuWu_DZDeviceInOutList {

    /**
     * ResultCode : 0
     * ResultText : 查询成功
     * DataTypeCode : ChuZuWu_DZDeviceInOutList
     * TaskID : 1
     * Content : {"HOUSEID":"852307E776C8440AB9EFB2963C086392","ROOMID":"2709689ea0e54313a10fd49d4b2317ca",
     * "DEVICEID":"aa862366df874bffb8d2a3ba3cf9be99","DEVICECODE":"426","PERSONNELINFOLIST":[{"MAXHEIGHT":"147",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1","DEVICETIME":"2018-02-06 22:29:09"},{"MAXHEIGHT":"147",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1","DEVICETIME":"2018-02-06 22:27:03"},{"MAXHEIGHT":"141",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1","DEVICETIME":"2018-02-06 12:23:17"},{"MAXHEIGHT":"141",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1","DEVICETIME":"2018-02-06 12:21:11"},{"MAXHEIGHT":"170",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"其它","DEVICETIME":"2018-02-05 20:37:41"},{"MAXHEIGHT":"170",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"其它","DEVICETIME":"2018-02-05 20:37:14"},{"MAXHEIGHT":"172,175",
     * "PEOPLENUMBER":"2","INOROUT":"出门,出门","STATE":"其它","DEVICETIME":"2018-02-05 18:28:15"},{"MAXHEIGHT":"172,175",
     * "PEOPLENUMBER":"2","INOROUT":"出门,出门","STATE":"其它","DEVICETIME":"2018-02-05 18:27:49"},{"MAXHEIGHT":"173",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1","DEVICETIME":"2018-02-05 16:21:50"},{"MAXHEIGHT":"173",
     * "PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1","DEVICETIME":"2018-02-05 16:21:23"}]}
     * StableVersion : 2.5.9
     */

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
        /**
         * HOUSEID : 852307E776C8440AB9EFB2963C086392
         * ROOMID : 2709689ea0e54313a10fd49d4b2317ca
         * DEVICEID : aa862366df874bffb8d2a3ba3cf9be99
         * DEVICECODE : 426
         * PERSONNELINFOLIST : [{"MAXHEIGHT":"147","PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1",
         * "DEVICETIME":"2018-02-06 22:29:09"},{"MAXHEIGHT":"147","PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1",
         * "DEVICETIME":"2018-02-06 22:27:03"},{"MAXHEIGHT":"141","PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1",
         * "DEVICETIME":"2018-02-06 12:23:17"},{"MAXHEIGHT":"141","PEOPLENUMBER":"1","INOROUT":"进门","STATE":"状态1",
         * "DEVICETIME":"2018-02-06 12:21:11"},{"MAXHEIGHT":"170","PEOPLENUMBER":"1","INOROUT":"进门","STATE":"其它",
         * "DEVICETIME":"2018-02-05 20:37:41"},{"MAXHEIGHT":"170","PEOPLENUMBER":"1","INOROUT":"进门","STATE":"其它",
         * "DEVICETIME":"2018-02-05 20:37:14"},{"MAXHEIGHT":"172,175","PEOPLENUMBER":"2","INOROUT":"出门,出门",
         * "STATE":"其它","DEVICETIME":"2018-02-05 18:28:15"},{"MAXHEIGHT":"172,175","PEOPLENUMBER":"2","INOROUT":"出门,
         * 出门","STATE":"其它","DEVICETIME":"2018-02-05 18:27:49"},{"MAXHEIGHT":"173","PEOPLENUMBER":"1","INOROUT":"进门",
         * "STATE":"状态1","DEVICETIME":"2018-02-05 16:21:50"},{"MAXHEIGHT":"173","PEOPLENUMBER":"1","INOROUT":"进门",
         * "STATE":"状态1","DEVICETIME":"2018-02-05 16:21:23"}]
         */

        private String HOUSEID;
        private String ROOMID;
        private String DEVICEID;
        private String DEVICECODE;
        private List<PERSONNELINFOLISTBean> PERSONNELINFOLIST;

        public String getHOUSEID() {
            return HOUSEID;
        }

        public void setHOUSEID(String HOUSEID) {
            this.HOUSEID = HOUSEID;
        }

        public String getROOMID() {
            return ROOMID;
        }

        public void setROOMID(String ROOMID) {
            this.ROOMID = ROOMID;
        }

        public String getDEVICEID() {
            return DEVICEID;
        }

        public void setDEVICEID(String DEVICEID) {
            this.DEVICEID = DEVICEID;
        }

        public String getDEVICECODE() {
            return DEVICECODE;
        }

        public void setDEVICECODE(String DEVICECODE) {
            this.DEVICECODE = DEVICECODE;
        }

        public List<PERSONNELINFOLISTBean> getPERSONNELINFOLIST() {
            return PERSONNELINFOLIST;
        }

        public void setPERSONNELINFOLIST(List<PERSONNELINFOLISTBean> PERSONNELINFOLIST) {
            this.PERSONNELINFOLIST = PERSONNELINFOLIST;
        }

        public static class PERSONNELINFOLISTBean {
            /**
             * MAXHEIGHT : 147
             * PEOPLENUMBER : 1
             * INOROUT : 进门
             * STATE : 状态1
             * DEVICETIME : 2018-02-06 22:29:09
             */

            private String MAXHEIGHT;
            private String PEOPLENUMBER;
            private String INOROUT;
            private String STATE;
            private String DEVICETIME;

            public String getMAXHEIGHT() {
                return MAXHEIGHT;
            }

            public void setMAXHEIGHT(String MAXHEIGHT) {
                this.MAXHEIGHT = MAXHEIGHT;
            }

            public String getPEOPLENUMBER() {
                return PEOPLENUMBER;
            }

            public void setPEOPLENUMBER(String PEOPLENUMBER) {
                this.PEOPLENUMBER = PEOPLENUMBER;
            }

            public String getINOROUT() {
                return INOROUT;
            }

            public void setINOROUT(String INOROUT) {
                this.INOROUT = INOROUT;
            }

            public String getSTATE() {
                return STATE;
            }

            public void setSTATE(String STATE) {
                this.STATE = STATE;
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
