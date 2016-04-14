package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/30 15:16
 * 修改备注：
 */
public class Param_ChuZuWu_AddRoomList {

    private String TaskID;
    private String HOUSEID;
    private int ROOMCOUNT;
    private List<ROOMLISTBean> ROOMLIST;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public String getHOUSEID() {
        return HOUSEID;
    }

    public void setHOUSEID(String HOUSEID) {
        this.HOUSEID = HOUSEID;
    }

    public int getROOMCOUNT() {
        return ROOMCOUNT;
    }

    public void setROOMCOUNT(int ROOMCOUNT) {
        this.ROOMCOUNT = ROOMCOUNT;
    }

    public List<ROOMLISTBean> getROOMLIST() {
        return ROOMLIST;
    }

    public void setROOMLIST(List<ROOMLISTBean> ROOMLIST) {
        this.ROOMLIST = ROOMLIST;
    }

    public static class ROOMLISTBean {
        private String ROOMID;
        private int ROOMNO;
        private int FIXTURE;
        private int SQUARE;
        private int PRICE;
        private int SHI;
        private int TING;
        private int WEI;
        private int YANGTAI;
        private int GALLERYFUL;
        private int DEPOSIT;
        private int ISAUTOPUBLISH;
        private String TITLE;
        private int DEVICECOUNT;
        private int PHOTOCOUNT;

        private List<DEVICEBean> DEVICE;

        private List<PHOTOBean> PHOTO;

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

        public int getFIXTURE() {
            return FIXTURE;
        }

        public void setFIXTURE(int FIXTURE) {
            this.FIXTURE = FIXTURE;
        }

        public int getSQUARE() {
            return SQUARE;
        }

        public void setSQUARE(int SQUARE) {
            this.SQUARE = SQUARE;
        }

        public int getPRICE() {
            return PRICE;
        }

        public void setPRICE(int PRICE) {
            this.PRICE = PRICE;
        }

        public int getSHI() {
            return SHI;
        }

        public void setSHI(int SHI) {
            this.SHI = SHI;
        }

        public int getTING() {
            return TING;
        }

        public void setTING(int TING) {
            this.TING = TING;
        }

        public int getWEI() {
            return WEI;
        }

        public void setWEI(int WEI) {
            this.WEI = WEI;
        }

        public int getYANGTAI() {
            return YANGTAI;
        }

        public void setYANGTAI(int YANGTAI) {
            this.YANGTAI = YANGTAI;
        }

        public int getGALLERYFUL() {
            return GALLERYFUL;
        }

        public void setGALLERYFUL(int GALLERYFUL) {
            this.GALLERYFUL = GALLERYFUL;
        }

        public int getDEPOSIT() {
            return DEPOSIT;
        }

        public void setDEPOSIT(int DEPOSIT) {
            this.DEPOSIT = DEPOSIT;
        }

        public int getISAUTOPUBLISH() {
            return ISAUTOPUBLISH;
        }

        public void setISAUTOPUBLISH(int ISAUTOPUBLISH) {
            this.ISAUTOPUBLISH = ISAUTOPUBLISH;
        }

        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public int getDEVICECOUNT() {
            return DEVICECOUNT;
        }

        public void setDEVICECOUNT(int DEVICECOUNT) {
            this.DEVICECOUNT = DEVICECOUNT;
        }

        public int getPHOTOCOUNT() {
            return PHOTOCOUNT;
        }

        public void setPHOTOCOUNT(int PHOTOCOUNT) {
            this.PHOTOCOUNT = PHOTOCOUNT;
        }

        public List<DEVICEBean> getDEVICE() {
            return DEVICE;
        }

        public void setDEVICE(List<DEVICEBean> DEVICE) {
            this.DEVICE = DEVICE;
        }

        public List<PHOTOBean> getPHOTO() {
            return PHOTO;
        }

        public void setPHOTO(List<PHOTOBean> PHOTO) {
            this.PHOTO = PHOTO;
        }

        public static class DEVICEBean {
            private String DEVICEID;
            private int DEVICETYPE;
            private int DEVICECODE;
            private String DEVICENAME;

            public String getDEVICEID() {
                return DEVICEID;
            }

            public void setDEVICEID(String DEVICEID) {
                this.DEVICEID = DEVICEID;
            }

            public int getDEVICETYPE() {
                return DEVICETYPE;
            }

            public void setDEVICETYPE(int DEVICETYPE) {
                this.DEVICETYPE = DEVICETYPE;
            }

            public int getDEVICECODE() {
                return DEVICECODE;
            }

            public void setDEVICECODE(int DEVICECODE) {
                this.DEVICECODE = DEVICECODE;
            }

            public String getDEVICENAME() {
                return DEVICENAME;
            }

            public void setDEVICENAME(String DEVICENAME) {
                this.DEVICENAME = DEVICENAME;
            }
        }

        public static class PHOTOBean {
            private String LISTID;
            private String TAG;
            private String IMAGE;

            public String getLISTID() {
                return LISTID;
            }

            public void setLISTID(String LISTID) {
                this.LISTID = LISTID;
            }

            public String getTAG() {
                return TAG;
            }

            public void setTAG(String TAG) {
                this.TAG = TAG;
            }

            public String getIMAGE() {
                return IMAGE;
            }

            public void setIMAGE(String IMAGE) {
                this.IMAGE = IMAGE;
            }
        }
    }
}
