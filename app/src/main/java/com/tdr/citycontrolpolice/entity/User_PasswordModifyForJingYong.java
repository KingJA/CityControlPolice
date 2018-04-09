package com.tdr.citycontrolpolice.entity;

/**
 * Description:TODO
 * Create Time:2018/4/9 10:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User_PasswordModifyForJingYong {

    /**
     * ResultCode : 0
     * ResultText : 修改成功
     * DataTypeCode : User_ PasswordModifyForJingYong
     * TaskID :  1
     * Content : {}
     */

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
    }
}
