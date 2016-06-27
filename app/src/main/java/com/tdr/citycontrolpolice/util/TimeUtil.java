package com.tdr.citycontrolpolice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/6 10:50
 * 修改备注：
 */
public class TimeUtil {

    public static boolean isOneDay(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date lastDate = format.parse(time);
            Date currentDate = new Date();
            long between = currentDate.getTime() - lastDate.getTime();
            if (between < (24 * 60 * 60 * 1000)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取标准时间格式
     *
     * @return
     */
    public static String getFormatTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    public static String getFormatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    public static boolean compareDate(String startDate,String endDate){
        if(startDate.compareTo(endDate)>0){
            ToastUtil.showMyToast("请核对日期先后");
           return false;
        }
        return true;
    }
    public static boolean compareTime(String startTime,String endTime){
        if(startTime.compareTo(endTime)>0){
            ToastUtil.showMyToast("请核对时间先后");
           return false;
        }
        return true;
    }


    public static String Date2String(Date date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }

    public static Date String2Date(String strDate) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }
}
