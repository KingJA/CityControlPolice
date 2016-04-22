package com.tdr.citycontrolpolice.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/22 9:05
 * 修改备注：
 */
public class NfcConstants {
    public static final String ACTION_IDENTITY = "com.tdr.identity.readcard";
    public static final String ACTION_ECARD = "com.tdr.ecard.readcard";
    public static final String PERMSISION = "com.tdr.tendencyNfc.ReadCard";

    /**
     * Nfc相关字段
     */
    public static final String SUCCESSED = "1";

    public static final String STATE_ALLOW = "1";
    public static final String STATE_NOT_ALLOW = "2";

    public static final String ACTION_SENDCARD = "1";
    public static final String ACTION_SUPPLECARD = "2";

    public static final String COMMID_MADECARD = "101";// 制成人卡命令
    public static final String COMMID_MADECHILDCARD = "111";// 制儿童卡命令
    public static final String COMMID_UPDATECARD = "104";// 更新成人卡
    public static final String COMMID_UPDATECHILDCARD = "114";// 更新儿童卡

    public static final int NFC_ERROR = -1;
    public static final String KEY_CARD_ID = "id";
    public static final String KEY_CARD_TYPE = "CARD_TYPE";
    public static final String KEY_ID_CARD = "ID_CARD";
    public static final String KEY_AREA = "AREA";
    public static final String KEY_PHONE_NUM = "PHONE_NUM";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_CURRENT_ADDRESS = "CURRENT_ADDRESS";
    public static final String KEY_PRIMARY_ADDRESS = "PRIMARY_ADDRESS";
    public static final String KEY_CHILD_ID = "CHILD_ID";
    public static final String KEY_CHILD_NAME = "CHILD_NAME";
    public static final String KEY_ERRORCODE = "ERRORCODE";


    public static String imei;
    public static String authentication;// 认证码


    public static final int HANDLER_KEY_GETVERSION_SUCCESS = 0;
    public static final int HANDLER_KEY_GETVERSION_FAIL = HANDLER_KEY_GETVERSION_SUCCESS + 1;

    public static final int MESSAGE_VALID_NFCBUTTON = HANDLER_KEY_GETVERSION_FAIL + 1;

    public static final int HANDLER_KEY_SENDIDENTITY = MESSAGE_VALID_NFCBUTTON + 1;

    public static final int HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS = HANDLER_KEY_SENDIDENTITY + 1;
    public static final int HANDLER_KEY_GETIDENTITY_LOCAL_FAIL = HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS + 1;

    public static final int HANDLER_KEY_GETECARD_SUCCESS = HANDLER_KEY_GETIDENTITY_LOCAL_FAIL + 1;

    /**
     * 访问地址
     */
//	public static final String URL = "http://172.18.18.21:8891/AppHandler.ashx";// 省研究院地址
//	public static final String URL = "http://192.168.1.22:18429/AppHandler.ashx";// 测试
//	public static final String URL = "http://127.0.0.1:8891/AppHandler.ashx";// 公安内网
    public static final String URL = "http://172.18.18.38:8891/AppHandler.ashx";// 公安交通
    // public static final String URL =
    // "http://127.0.0.1:8891/AppHandler.ashx";// 温州市正式地址

    /**
     * 身份证字段存储
     */
    public static final String STATE = "state";
    public static final String TAGID = "tagId";
    public static final String NAME = "name";
    public static final String SEX = "sex";
    public static final String NATION = "nation";
    public static final String BIRTHDAY = "birthday";
    public static final String ADDRESS = "address";
    public static final String IDENTITY = "identity";
    public static final String POLICE = "police";
    public static final String VALIDITY = "validity";
    public static final String DNCODE = "DNcode";

    /**
     * E居卡
     */
    public static final String CARD_ID = "CARD_ID";
    public static final String CARD_TYPE = "CARD_TYPE";
    public static final String ID_CARD = "ID_CARD";
    public static final String PHONE_NUM = "PHONE_NUM";
    public static final String E_NAME = "E_NAME";
    public static final String CHILD_ID = "CHILD_ID";
    public static final String CHILD_NAME = "CHILD_NAME";
    public static final String CURRENT_ADDRESS = "CURRENT_ADDRESS";
    public static final String PRIMARY_ADDRESS = "PRIMARY_ADDRESS";

    /**
     * 获取本机IMEI号
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 自定义Toast
     *
     * @param context
     * @param msg
     */
    public static final void myToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, 5000);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
