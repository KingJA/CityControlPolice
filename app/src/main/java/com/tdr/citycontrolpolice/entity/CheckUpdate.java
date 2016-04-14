package com.tdr.citycontrolpolice.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/2/26.
 */
public class CheckUpdate {
    private String TaskID;
    private List<UpDatas> Datas;

    public List<UpDatas> getDatas() {
        return Datas;
    }

    public void setDatas(List<UpDatas> datas) {
        Datas = datas;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }
}
