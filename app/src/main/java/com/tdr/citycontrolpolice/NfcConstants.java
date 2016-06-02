package com.tdr.citycontrolpolice;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

public class NfcConstants {

    public static final String ACTION_IDENTITY = "com.tdr.identity.readcard";
    public static final String ACTION_ECARD = "com.tdr.ecard.readcard";
    public static final String PERMSISION = "com.tdr.tendencyNfc.ReadCard";

    /**
     * Nfc����ֶ�
     */
    public static final String SUCCESSED = "1";

    public static final String STATE_ALLOW = "1";
    public static final String STATE_NOT_ALLOW = "2";

    public static final String ACTION_SENDCARD = "1";
    public static final String ACTION_SUPPLECARD = "2";

    public static final String COMMID_MADECARD = "101";// �Ƴ��˿�����
    public static final String COMMID_MADECHILDCARD = "111";// �ƶ�ͯ������
    public static final String COMMID_UPDATECARD = "104";// ���³��˿�
    public static final String COMMID_UPDATECHILDCARD = "114";// ���¶�ͯ��

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
    public static String authentication;// ��֤��


    public static final int HANDLER_KEY_GETVERSION_SUCCESS = 0;
    public static final int HANDLER_KEY_GETVERSION_FAIL = HANDLER_KEY_GETVERSION_SUCCESS + 1;

    public static final int MESSAGE_VALID_NFCBUTTON = HANDLER_KEY_GETVERSION_FAIL + 1;

    public static final int HANDLER_KEY_SENDIDENTITY = MESSAGE_VALID_NFCBUTTON + 1;

    public static final int HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS = HANDLER_KEY_SENDIDENTITY + 1;

    public static final int HANDLER_KEY_GETIDENTITY_LOCAL_FAIL = HANDLER_KEY_GETIDENTITY_LOCAL_SUCCESS + 1;

    public static final int HANDLER_KEY_GETECARD_SUCCESS = HANDLER_KEY_GETIDENTITY_LOCAL_FAIL + 1;

    public static final String URL = "http://127.0.0.1:8891/AppHandler.ashx";// ��������

    /**
     * ���֤�ֶδ洢
     */
    public static final String STATE = "state";//���֤״̬�֣�2��������ݳ�ʱ��41������ʧ�ܣ�42��û���ҵ���������43������æ��90�������ɹ�
    public static final String TAGID = "tagId";//��ƬID
    public static final String NAME = "name";//����
    public static final String SEX = "sex";//�Ա��С�Ů
    public static final String NATION = "nation";//���壬��....
    public static final String BIRTHDAY = "birthday";//�������£�19900101
    public static final String ADDRESS = "address";//סַ
    public static final String IDENTITY = "identity";//���֤
    public static final String POLICE = "police";//��֤���
    public static final String VALIDITY = "validity";//��Ч��
    public static final String DNCODE = "DNcode";//DN��

    /**
     * E�ӿ�
     */
    public static final String CARD_ID = "CARD_ID";//E�ӿ�Id
    public static final String CARD_TYPE = "CARD_TYPE";//��Ƭ����
    public static final String ID_CARD = "ID_CARD";//���֤
    public static final String PHONE_NUM = "PHONE_NUM";//��ϵ��ʽ
    public static final String E_NAME = "E_NAME";//����
    public static final String CHILD_ID = "CHILD_ID";//��ͯ���֤���ɱ�����ɣ�
    public static final String CHILD_NAME = "CHILD_NAME";//��ͯ����
    public static final String CURRENT_ADDRESS = "CURRENT_ADDRESS";//��סַ
    public static final String PRIMARY_ADDRESS = "PRIMARY_ADDRESS";//����סַ

    /**
     * ��ȡ����IMEI��
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
     * �Զ���Toast
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
