package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/29 13:48
 * 修改备注：
 */
public class Basic_PaiChuSuo_Return {

    private int ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;

    private List<Basic_PaiChuSuo_Kj> Content;

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


    public List<Basic_PaiChuSuo_Kj> getContent() {
        return Content;
    }

    public void setContent(List<Basic_PaiChuSuo_Kj> content) {
        Content = content;
    }
}
