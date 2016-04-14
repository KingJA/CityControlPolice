package com.tdr.citycontrolpolice.util;

import org.apache.commons.codec.binary.Base64;

public class TendencyEncrypt {

    /**
     * Դ�ַ�
     */
    private static final char[] SSCode = new char[]{'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '0', '+', '='};

    /**
     * ת���ַ�
     */
    private static final char[] EnCode = new char[]{'1', '2', 'b', 'W', 'd',
            'e', 'f', 'Y', 'R', 'S', 'T', 'U', 'V', '=', 'X', 'Q', 'g', 'h',
            'i', '4', '9', '0', 'm', 'n', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'K', 'L', 'M', 'P', 'I', 'J', 'N', 'O', 's', 't', 'u', 'v',
            'o', 'p', 'q', 'r', '5', '6', '7', '8', 'w', 'x', 'y', 'z', 'Z',
            'a', '3', 'j', 'k', 'l', '+', 'c'};

    private static int GetSSCodeIndex(char chr) {
        for (int i = 0; i < SSCode.length; i++) {
            if (SSCode[i] == chr) {
                return i;
            }
        }
        return -1;
    }

    private static int GetEnCodeIndex(char chr) {
        for (int i = 0; i < EnCode.length; i++) {
            if (EnCode[i] == chr) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ����
     *
     * @param base64string
     * @return
     */
    public static String encrypt(String base64string) {
        String outString = "";
        for (int i = 0; i < base64string.length(); i++) {
            String getString = base64string.substring(i, i + 1);
            int getIndex = GetSSCodeIndex(getString.toCharArray()[0]);
            if (getIndex == -1) {
                outString += getString;
            } else {
                outString += EnCode[getIndex];
            }
        }
        return outString;
    }

    /**
     * ������
     *
     * @param base64string
     * @return
     */
    public static String decrypt(String base64string) {//http://wz.app.iotone.cn/?baVWd5V96WV415VW168jsc
        String outString = "";
        for (int i = 0; i < base64string.length(); i++) {
            String getString = base64string.substring(i, i + 1);
            int getIndex = GetEnCodeIndex(getString.toCharArray()[0]);
            if (getIndex == -1) {
                outString += getString;
            } else {
                outString += SSCode[getIndex];//R
            }
        }
        return outString;
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 转16进制
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
