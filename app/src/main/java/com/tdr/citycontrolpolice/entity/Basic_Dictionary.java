package com.tdr.citycontrolpolice.entity;


import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/2/3.
 */
@Table(name = "Basic_Dictionary")
public class Basic_Dictionary {
    @Id(column = "LISTID")
    private String LISTID;
    private String CODETYPE;
    private String COLUMNCODE;
    private String COLUMNNAME;
    private String COLUMNVALUE;
    private String COLUMNCOMMENT;
    private String TABLENAME;
    private String PROTOCOLTYPE;
    private String UNIT;
    private String ISSHOW;
    private String USERANG;
    private String ISVALID;
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
