package com.tdr.citycontrolpolice.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 16:25
 * 修改备注：
 */
public class CheckUtil {
    /**
     * 非空验证
     *
     * @param s
     * @param tip
     * @return
     */
    public static boolean checkEmpty(String s, String tip) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showMyToast(tip);
            return false;
        }
        return true;
    }

    /**
     * 验证是否以0开始
     * @param s
     * @return
     */
    public static boolean checkZero(String s) {
        if ("0".equals(s) || s.startsWith("0")) {
            ToastUtil.showMyToast("请输入非零数字");
            return false;
        }
        return true;
    }


    /**
     * 手机号码验证
     * @param phone
     * @return
     */
    public static boolean checkPhoneFormat(String phone) {
        // 判断非空
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showMyToast("手机号码不能为空");
            return false;
        }

        // 判断手机号格式
        if (!Pattern.matches(
                "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$", phone)) {
            ToastUtil.showMyToast("手机号码格式不对");
            return false;
        }
        return true;
    }

    /**
     * 密码格式验证
     * @param password
     * @return
     */
    public static boolean checkPasswordFormat(String password) {
        // 判断非空
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showMyToast("密码不能为空");
            return false;
        }
        if (!Pattern.matches("[a-zA-Z0-9]{1,12}", password)) {
            ToastUtil.showMyToast("密码须为1-12位字母或数字组合");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检查字符串长度
     *
     * @param input
     * @param lenght
     * @param tip
     * @return
     */
    public static boolean checkLength(String input, int lenght, String tip) {

        if (input.length() > lenght) {
            ToastUtil.showMyToast(tip);
            return false;
        }
        return true;
    }

    /**
     * 校验18位身份证号码
     *
     * @param value
     * @return
     */
    public static boolean isIDCard18(final String value) {
        if (value == null || value.length() != 18)
            return false;
        if (!value.matches("[\\d]+[X]?"))
            return false;
        String code = "10X98765432";
        int weight[] = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        int nSum = 0;
        for (int i = 0; i < 17; ++i) {
            nSum += (int) (value.charAt(i) - '0') * weight[i];
        }
        int nCheckNum = nSum % 11;
        char chrValue = value.charAt(17);
        char chrCode = code.charAt(nCheckNum);
        if (chrValue == chrCode)
            return true;
        if (nCheckNum == 2 && (chrValue + ('a' - 'A') == chrCode))
            return true;
        return false;
    }
}
