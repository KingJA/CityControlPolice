package com.tdr.citycontrolpolice.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * 使用单例模式实例化User对象
 *
 * @author Administrator
 */
public class UserService {
    private static Context context;
    private static UserService instance;

    /**
     * 将构造函数定义私有，不允许外部实例化该对象，而只能使用getInstance()方法
     */
    private UserService() {
    }

    /**
     * 获取User的实例
     *
     * @param context 外部传入Android上下文
     * @return
     */
    public static UserService getInstance(Context context) {
        UserService.context = context;
        if (UserService.instance == null) {
            return new UserService();
        }
        return instance;
    }

    /**
     * 取得用户ID
     *
     * @return
     */
    public String getUid() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString("uid", "");
    }

    /**
     * 取得更新时间
     *
     * @return
     */
    public String getUpDate() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString("upDate", "");
    }

    /**
     * 取得用户TOKEN
     *
     * @return
     */
    public String getToken() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString("token", "");
    }

    /*
     * 第一次登录
     *
     */
    public int getOnly() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getInt("only", 0);
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogin() {
        if (getUid().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户名
     *
     * @return String 用户名
     */
    public String getUsername() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString("username", "");
    }

    public String getProjectname() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString("projectName", "");
    }

    public String getProjectTypename() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString("projectTypeName", "");
    }

    public int getProjectId() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getInt("projectId", 0);
    }

    public String getServiceUid() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString("serviceUid", "");
    }

    public int getProjectType() {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getInt("projectType", 0);
    }

    public String getUpDate(String s) {
        SharedPreferences sp = UserService.context.getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        return sp.getString(s, "2011-03-15 13:03:42");
    }

    /**
     * 退出登录
     */
    public Boolean logout() {
        String PREFS_NAME = Constants.USER_DETAILS;
        ;
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", 0);
        editor.commit();
        Toast.makeText(context, "退出登录成功!", Toast.LENGTH_LONG).show();
        return true;
    }
}
