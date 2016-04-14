package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：2.6.	标准地址关键字检索
 * 创建人：KingJA
 * 创建时间：2016/4/13 11:21
 * 修改备注：
 */
public class Basic_StandardAddressCodeByKey_Kj {

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
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
        private String id;
        private String address;
        private String addressPath;
        private String addressClass;
        private String geocode;
        private double x;
        private double y;
        private int score;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressPath() {
            return addressPath;
        }

        public void setAddressPath(String addressPath) {
            this.addressPath = addressPath;
        }

        public String getAddressClass() {
            return addressClass;
        }

        public void setAddressClass(String addressClass) {
            this.addressClass = addressClass;
        }

        public String getGeocode() {
            return geocode;
        }

        public void setGeocode(String geocode) {
            this.geocode = geocode;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
