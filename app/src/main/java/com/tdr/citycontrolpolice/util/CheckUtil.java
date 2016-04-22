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
    public static boolean checkEmpty(String s, String tip) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showMyToast(tip);
            return false;
        }
        return true;
    }
    public static boolean checkZero(String s) {
        if ("0".equals(s)) {
            ToastUtil.showMyToast("请输入非零数字");
            return false;
        }
        return true;
    }

    public static boolean checkUserName(String userName) {
        if (TextUtils.isEmpty(userName)) {
            ToastUtil.showMyToast("用户名不能为空");
            return false;
        }
        return true;
    }

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

    public static boolean checkLength(String input, int lenght, String tip) {

        if (input.length() > lenght) {
            ToastUtil.showMyToast(tip);
            return false;
        }
        return true;
    }
}
