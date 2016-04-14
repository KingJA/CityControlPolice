package com.tdr.citycontrolpolice.entity;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：登录
 * 创建人：KingJA
 * 创建时间：2016/3/28 14:58
 * 修改备注：
 */
@Table(name = "Basic_Dictionary")
public class Basic_Dictionary_Kj {
    @Column(name = "LISTID", isId = true)
    private String LISTID;
    @Column(name = "CODETYPE")
    private String CODETYPE;
    @Column(name = "COLUMNCODE")
    private String COLUMNCODE;
    @Column(name = "COLUMNNAME")
    private String COLUMNNAME;
    @Column(name = "COLUMNVALUE")
    private String COLUMNVALUE;
    @Column(name = "COLUMNCOMMENT")
    private String COLUMNCOMMENT;
    @Column(name = "TABLENAME")
    private String TABLENAME;
    @Column(name = "PROTOCOLTYPE")
    private String PROTOCOLTYPE;
    @Column(name = "UNIT")
    private String UNIT;
    @Column(name = "ISSHOW")
    private String ISSHOW;
    @Column(name = "USERANG")
    private String USERANG;
    @Column(name = "ISVALID")
    private String ISVALID;
    @Column(name = "UPDATETIME")
    private String UPDATETIME;


    public String getLISTID() {
        return LISTID;
    }

    public void setLISTID(String LISTID) {
        this.LISTID = LISTID;
    }

    public String getCODETYPE() {
        return CODETYPE;
    }

    public void setCODETYPE(String CODETYPE) {
        this.CODETYPE = CODETYPE;
    }

    public String getCOLUMNCODE() {
        return COLUMNCODE;
    }

    public void setCOLUMNCODE(String COLUMNCODE) {
        this.COLUMNCODE = COLUMNCODE;
    }

    public String getCOLUMNNAME() {
        return COLUMNNAME;
    }

    public void setCOLUMNNAME(String COLUMNNAME) {
        this.COLUMNNAME = COLUMNNAME;
    }

    public String getCOLUMNVALUE() {
        return COLUMNVALUE;
    }

    public void setCOLUMNVALUE(String COLUMNVALUE) {
        this.COLUMNVALUE = COLUMNVALUE;
    }

    public String getCOLUMNCOMMENT() {
        return COLUMNCOMMENT;
    }

    public void setCOLUMNCOMMENT(String COLUMNCOMMENT) {
        this.COLUMNCOMMENT = COLUMNCOMMENT;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getPROTOCOLTYPE() {
        return PROTOCOLTYPE;
    }

    public void setPROTOCOLTYPE(String PROTOCOLTYPE) {
        this.PROTOCOLTYPE = PROTOCOLTYPE;
    }

    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String UNIT) {
        this.UNIT = UNIT;
    }

    public String getISSHOW() {
        return ISSHOW;
    }

    public void setISSHOW(String ISSHOW) {
        this.ISSHOW = ISSHOW;
    }

    public String getUSERANG() {
        return USERANG;
    }

    public void setUSERANG(String USERANG) {
        this.USERANG = USERANG;
    }

    public String getISVALID() {
        return ISVALID;
    }

    public void setISVALID(String ISVALID) {
        this.ISVALID = ISVALID;
    }

    public String getUPDATETIME() {
        return UPDATETIME;
    }

    public void setUPDATETIME(String UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }
}
