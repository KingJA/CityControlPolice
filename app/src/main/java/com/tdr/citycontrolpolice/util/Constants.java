package com.tdr.citycontrolpolice.util;

/**
 * Created by Administrator on 2016/1/20.
 */
public class Constants {

    public static final int HANDLER_KEY_GETVERSION_SUCCESS = 0;// 获取版本号成功为最初的参数“0”
    public static final int HANDLER_KEY_GETVERSION_FAIL = HANDLER_KEY_GETVERSION_SUCCESS + 1;
    /*测试接口*/
    public static final String WEBSERVER_URL = "http://zafkapp.test.iotone.cn/rentalestate.asmx"; //正式接口

    /*温州版接口*/
//    public static final String WEBSERVER_URL = "http://127.0.0.1:8002/rentalestate.asmx";//温州版
    public static final String NFC_URL = "http://127.0.0.1:8891/AppHandler.ashx";//温州NFC地址
//    public static final String NFC_IP = "127.0.0.1";//温州NFC IP


    /*省厅版接口*/
//    public static final String WEBSERVER_URL = "http://172.18.18.21:8002/RentalEstate.asmx";//省厅地址
//    public static final String NFC_URL = "http://172.18.18.38:8891/AppHandler.ashx";//省厅NFC地址
//    public static final String NFC_IP = "172.18.18.38";//省厅NFC IP

    /*温州内侧版接口*/
//    public static final String WEBSERVER_URL = "http://127.0.0.1:8890/rentalestate.asmx";//温州内测版
    public static final String NFC_IP = "103.21.119.78";//温州NFC IP

    public static final String WEBSERVER_NAMESPACE = "http://tempuri.org/";
    public static final String WEBSERVER_LOGIN = "Login";// 用户名密码登录
    public static final String WEBSERVER_REGISTERUSER = "RegisterUser ";// 注册
    public static final String WEBSERVER_SENDVALIDCODE = "sendValidCode";// 获取验证码
    public static final String WEBSERVER_MODIFYPWD = "ModifyPwd";// 修改密码
    public static final String WEBSERVER_CHANGEPHOTO = "ChangePhoto";// 更换图片
    public static final String WEBSERVER_PUBLICSECURITYCONTROLAPP = "RERequest";// 治安防控APP接口
    public static final String WEBSERVER_RESETPWDFORZAFK = "ResetPwdForZAFK";// 治安管控重置密码
    public static final String WEBSERVER_AUTOLOGIN = "AutoLogin";//	自动登录
    public static final String WEBSERVER_CHECKUSER = "checkUser";// 验证用户
    public static final String WEBSERVER_VERIFYNEWPHONE = "VerifyNewPhone";// 验证新手机
    public static final String WEBSERVER_CHUZUWU_LIST = "ChuZuWu_List";//出租屋列表
    public static final String USER_DETAILS = "userDetails.xml";

}
