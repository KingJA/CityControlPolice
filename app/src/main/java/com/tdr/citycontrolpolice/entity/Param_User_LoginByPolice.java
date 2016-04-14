package com.tdr.citycontrolpolice.entity;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：警号登录请求体
 * 创建人：KingJA
 * 创建时间：2016/4/1 16:13
 * 修改备注：
 */
public class Param_User_LoginByPolice {

    private String TaskID;
    private String USERNAME;
    private String PASSWORD;
    private double SOFTVERSION;
    private int SOFTTYPE;
    private PHONEINFOBean PHONEINFO;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public double getSOFTVERSION() {
        return SOFTVERSION;
    }

    public void setSOFTVERSION(double SOFTVERSION) {
        this.SOFTVERSION = SOFTVERSION;
    }

    public int getSOFTTYPE() {
        return SOFTTYPE;
    }

    public void setSOFTTYPE(int SOFTTYPE) {
        this.SOFTTYPE = SOFTTYPE;
    }

    public PHONEINFOBean getPHONEINFO() {
        return PHONEINFO;
    }

    public void setPHONEINFO(PHONEINFOBean PHONEINFO) {
        this.PHONEINFO = PHONEINFO;
    }

    public static class PHONEINFOBean {
        private String SYSTEMTYPE;
        private String SYSTEMVERSION;
        private String DEVICEMODEL;
        private String DEVICEID;
        private String IMSI;
        private String IMEI;
        private String ICCID;
        private String WIFIMAC;
        private String BTMAC;

        public String getSYSTEMTYPE() {
            return SYSTEMTYPE;
        }

        public void setSYSTEMTYPE(String SYSTEMTYPE) {
            this.SYSTEMTYPE = SYSTEMTYPE;
        }

        public String getSYSTEMVERSION() {
            return SYSTEMVERSION;
        }

        public void setSYSTEMVERSION(String SYSTEMVERSION) {
            this.SYSTEMVERSION = SYSTEMVERSION;
        }

        public String getDEVICEMODEL() {
            return DEVICEMODEL;
        }

        public void setDEVICEMODEL(String DEVICEMODEL) {
            this.DEVICEMODEL = DEVICEMODEL;
        }

        public String getDEVICEID() {
            return DEVICEID;
        }

        public void setDEVICEID(String DEVICEID) {
            this.DEVICEID = DEVICEID;
        }

        public String getIMSI() {
            return IMSI;
        }

        public void setIMSI(String IMSI) {
            this.IMSI = IMSI;
        }

        public String getIMEI() {
            return IMEI;
        }

        public void setIMEI(String IMEI) {
            this.IMEI = IMEI;
        }

        public String getICCID() {
            return ICCID;
        }

        public void setICCID(String ICCID) {
            this.ICCID = ICCID;
        }

        public String getWIFIMAC() {
            return WIFIMAC;
        }

        public void setWIFIMAC(String WIFIMAC) {
            this.WIFIMAC = WIFIMAC;
        }

        public String getBTMAC() {
            return BTMAC;
        }

        public void setBTMAC(String BTMAC) {
            this.BTMAC = BTMAC;
        }
    }
}
