package com.tdr.citycontrolpolice.util;

import android.util.Log;

/**
 * Created by Administrator on 2016/1/20.
 */
public class Constants {

    public static final int RELEASE_SERVICE = 0;
/* =================================================================================================================   */

    public static final int TEST = 0;
    public static final int PSTORE = 1;
    public static final int LIUGUANTONG = 2;
    public static final int SHENGTING = 3;

    public static final int HANDLER_KEY_GETVERSION_SUCCESS = 0;
    public static final int HANDLER_KEY_GETVERSION_FAIL = 1;
    public static final String WEBVIEW_HOST = "http://test.iotone.cn:12020/"; //WebView测试主机
    public static final String JOB_LARAM = "info/app/jobalarm.aspx"; //工作预警
    public static final String JOB_TONGJI = "info/app/jobcount.aspx"; //工作统计
    public static final String COMMON_QUESTION = "info/app/faq.aspx"; //常见问题

    /*测试接口*/
//    public static final String WEBSERVER_URL = "http://zafkapp.test.iotone.cn/rentalestate.asmx"; //正式接口
//    public static final String WEBSERVER_URL = "http://appservice.wzga.tdr-cn.com/rentalestate.asmx";//外网正式库

    /*温州版接口*/
//    public static final String WEBSERVER_URL = "http://127.0.0.1:8002/rentalestate.asmx";//温州版
//    public static final String NFC_URL = "http://127.0.0.1:8891/AppHandler.ashx";//温州NFC地址
//    public static final String NFC_IP = "127.0.0.1";//温州NFC IP

    /*省厅版接口*/
//    public static final String WEBSERVER_URL = "http://172.18.18.21:8002/RentalEstate.asmx";//省厅地址
//    public static final String NFC_URL = "http://172.18.18.38:8891/AppHandler.ashx";//省厅NFC地址
//    public static final String NFC_IP = "172.18.18.38";//省厅NFC IP

    /*温州内侧版接口*/
//    public static final String WEBSERVER_URL = "http://127.0.0.1:8890/rentalestate.asmx";//温州内测版
//    public static final String NFC_IP = "103.21.119.78";//温州NFC IP

    public static final String WEBSERVER_NAMESPACE = "http://tempuri.org/";
    public static final String WEBSERVER_PUBLICSECURITYCONTROLAPP = "RERequest";// 治安防控APP接口
    public static final String USER_DETAILS = "userDetails.xml";
    public static final String DOOR_MARK = "http://xinjumin.ouhai.gov.cn:8060/zzsb";

    public static String getHostUrl() {
        String hostUrl = "";
        switch (RELEASE_SERVICE) {
            case TEST:
//                hostUrl = "http://192.168.168.161:8888/RentalEstate.asmx";
                hostUrl = "http://zafkapp.test.iotone.cn/rentalestate.asmx";
                break;
            case PSTORE:
            case LIUGUANTONG:
                hostUrl = "http://127.0.0.1:8002/rentalestate.asmx";
                break;
            case SHENGTING:
                hostUrl = "http://172.18.18.21:8002/RentalEstate.asmx";
//                hostUrl = "http://192.168.140.13:8801/RentalEstate.asmx";
                break;
        }
        Log.i("hostUrl", hostUrl);
        return hostUrl;
    }

    public static String getNFCUrl() {
        String nfcUrl = "";
        switch (RELEASE_SERVICE) {
            case TEST:
                nfcUrl = "http://127.0.0.1:8891/AppHandler.ashx";
                break;
            case PSTORE:
            case LIUGUANTONG:
                nfcUrl = "http://127.0.0.1:8891/AppHandler.ashx";
                break;
            case SHENGTING:
                nfcUrl = "http://172.18.18.38:8891/AppHandler.ashx";
                break;
        }
        return nfcUrl;
    }

    public static String getNFCIP() {
        String nfcIp = "";
        switch (RELEASE_SERVICE) {
            case TEST:
                nfcIp = "103.21.119.78";
                break;
            case PSTORE:
            case LIUGUANTONG:
                nfcIp = "127.0.0.1";
                break;
            case SHENGTING:
                nfcIp = "172.18.18.38";
                break;
        }
        return nfcIp;
    }

    public static String getUpdataUrl() {
        String updataUrl = "";
        switch (RELEASE_SERVICE) {
            case TEST:
                updataUrl = "http://dmi.tdr-cn.com/WebServiceAPKRead.asmx";
                break;
            case PSTORE:
            case LIUGUANTONG:
                updataUrl = "http://127.0.0.1:8890/WebServiceAPKRead.asmx";
                break;
            case SHENGTING:
                updataUrl = "http://172.18.18.21:8892/WebServiceAPKRead.asmx";
                break;
        }
        return updataUrl;
    }

    public static String getUpdataService() {
        String updataService = "";
        switch (RELEASE_SERVICE) {
            case TEST:
                updataService = "http://dmi.tdr-cn.com/newestapk/";
                break;
            case PSTORE:
            case LIUGUANTONG:
                updataService = "http://127.0.0.1:8890/newestapk/";
                break;
            case SHENGTING:
                updataService = "http://172.18.18.21:8892/newestapk/";
                break;
        }
        return updataService;
    }

}
