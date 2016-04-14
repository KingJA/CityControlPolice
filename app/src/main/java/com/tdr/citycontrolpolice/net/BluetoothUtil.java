package com.tdr.citycontrolpolice.net;

import android.util.Log;

import com.tdr.citycontrolpolice.util.ToastUtil;

import java.util.Arrays;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/2 14:21
 * 修改备注：
 */
public class BluetoothUtil {

    private static final String TAG = "BluetoothUtil";

    public static String bytesToHexString(byte[] bytes) {
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }

    /**
     * 若都不为空，将arrSrc2添加到arrSrc1的末尾组合成新的byte数组
     *
     * @param arrSrc1
     * @param arrSrc2
     * @return
     */
    public static final byte[] ByteArrayCopy(byte[] arrSrc1, byte[] arrSrc2) {
        byte[] arrDes = null;
        if (arrSrc2 == null) {
            arrDes = arrSrc1;
            return arrDes;
        }

        if (arrSrc1 != null) {
            arrDes = new byte[arrSrc1.length + arrSrc2.length];
            System.arraycopy(arrSrc1, 0, arrDes, 0, arrSrc1.length);
            System.arraycopy(arrSrc2, 0, arrDes, arrSrc1.length, arrSrc2.length);
        } else {
            arrDes = new byte[arrSrc2.length];
            System.arraycopy(arrSrc2, 0, arrDes, 0, arrSrc2.length);
        }

        return arrDes;
    }

    /**
     * 判断两个byte数组是否相等
     *
     * @param myByte
     * @param otherByte
     * @return
     */
    public static final boolean checkByte(byte[] myByte, byte[] otherByte) {
        boolean b = false;
        int len = myByte.length;
        if (len == otherByte.length) {
            for (int i = 0; i < len; i++) {
                if (myByte[i] != otherByte[i]) {
                    return b;
                }
            }
            b = true;
        }
        return b;
    }

    public static String getCardNo(byte[] totelByte) {
        byte[] startFlag = Arrays.copyOfRange(totelByte, 0, 1);//开始Flag 1位
        byte[] deviceId = Arrays.copyOfRange(totelByte, 1, 3);//设备ID 2位
        byte[] deviceType = Arrays.copyOfRange(totelByte, 3, 5);//设备类型 2位
        byte[] order = Arrays.copyOfRange(totelByte, 5, 6);//命令字 1位
        byte[] length = Arrays.copyOfRange(totelByte, 6, 8);//内容长度 1位
        byte[] cardType = Arrays.copyOfRange(totelByte, 8, 9);//卡类型 1位
        byte[] cardNO = Arrays.copyOfRange(totelByte, 9, 17);//卡号 8位
        byte[] name = Arrays.copyOfRange(totelByte, 17, 32);//姓名 15位
        byte[] cardNumber = Arrays.copyOfRange(totelByte, 32, 41);//身份证号 9位
        byte[] version = Arrays.copyOfRange(totelByte, 41, 42);//版本号 1位
        byte[] check = Arrays.copyOfRange(totelByte, 42, 43);//校验码 1位
        byte[] endFlag = Arrays.copyOfRange(totelByte, 43, 44);//结束Flag 1位
        if (cardType[0] == (byte) (0x01)) {
//            ToastUtil.showMyToast("身份证");
        } else if (cardType[0] == (byte) (0x02)) {
//            ToastUtil.showMyToast("IC卡");
        }
        if (cardType[0] == (byte) (0x03)) {
//            ToastUtil.showMyToast("流动");
        }
        Log.i(TAG, "getCardNo: " + bytesToHexString(cardNO));
        return bytesToHexString(cardNO);
    }
}
