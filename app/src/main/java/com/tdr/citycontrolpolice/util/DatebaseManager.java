package com.tdr.citycontrolpolice.util;

import android.app.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/1 9:56
 * 修改备注：
 */
public class DatebaseManager {
    private Activity context;
    private static DatebaseManager mDateBaseManager;

    private DatebaseManager(Activity context) {
        this.context = context;
    }

    public static DatebaseManager getInstance(Activity context) {
        if (mDateBaseManager == null) {
            synchronized (DatebaseManager.class) {
                if (mDateBaseManager == null) {
                    mDateBaseManager = new DatebaseManager(context);
                    return mDateBaseManager;
                }
            }
        }
        return mDateBaseManager;
    }

    /**
     * 复制数据库assets=>databases
     */
    public void copyDataBase(String dataBaseName) {
        long startTime = System.currentTimeMillis();
        File dateBasePath = new File("/data/data/" + context.getPackageName() + "/databases");
        File targetPath = new File("/data/data/" + context.getPackageName() + "/databases/" + dataBaseName);
        /**
         * 创建databases数据库文件夹
         */
        if (!dateBasePath.exists()) {
            dateBasePath.mkdir();
        }
        /**
         * 检查数据库文件是否存在
         */
        if (targetPath.exists() && targetPath.length() > 0) {
//            ToastUtil.showThreadToast(context, "数据库已经存在不用复制！");
            return;
        }
        try {
            InputStream inputStream = context.getAssets().open(dataBaseName);
            FileOutputStream outputStream = new FileOutputStream(targetPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            long endTime = System.currentTimeMillis();
//            ToastUtil.showThreadToast(context, "数据库复制成功,用时：" + String.valueOf(endTime - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDataBaseFile(String dataBaseName) {
        File dateBasePath = new File("/data/data/" + context.getPackageName() + "/databases");
        File targetPath = new File("/data/data/" + context.getPackageName() + "/databases/" + dataBaseName);
        if (dateBasePath.exists() && targetPath.delete()) {
            ToastUtil.showThreadToast(context, "数据库删除成功");
        }
    }
}
