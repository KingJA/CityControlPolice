package com.tdr.citycontrolpolice.entity;

import java.io.Serializable;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/30 21:23
 * 修改备注：
 */
public class ChuZuWu_RoomInfo implements Serializable{


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

    public static class ContentBean implements Serializable {
        private String HOUSEID;
        private String ROOMID;
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

        public String getROOMNO() {
            return ROOMNO;
        }

        public void setROOMNO(String ROOMNO) {
            this.ROOMNO = ROOMNO;
        }

        private String ROOMNO;

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
    }
}
