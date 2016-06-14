package com.tdr.citycontrolpolice.net;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tdr.citycontrolpolice.entity.Basic_CheckUpdate;
import com.tdr.citycontrolpolice.entity.Basic_CheckUpdate_Param;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Return;
import com.tdr.citycontrolpolice.entity.Basic_JuWeiHui_Kj;
import com.tdr.citycontrolpolice.entity.Basic_JuWeiHui_Return;
import com.tdr.citycontrolpolice.entity.Basic_PaiChuSuo_Kj;
import com.tdr.citycontrolpolice.entity.Basic_PaiChuSuo_Return;
import com.tdr.citycontrolpolice.entity.Basic_XingZhengQuHua_Kj;
import com.tdr.citycontrolpolice.entity.Basic_XingZhengQuHua_Return;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.TimeUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：数据下载工具类 (下载顺序：基础字典检查更新->升级，三实有检查更新->升级)
 * 创建人：KingJA
 * 创建时间：2016/4/29 10:39
 * 修改备注：
 */
public class DownloadDbManager {
    private static final String TAG = "DownloadDbManager";
    public static final String Basic_Dictionary = "Basic_Dictionary";
    public static final String Basic_JuWeiHui = "Basic_JuWeiHui";
    public static final String Basic_PaiChuSuo = "Basic_PaiChuSuo";
    public static final String Basic_XingZhengQuHua = "Basic_XingZhengQuHua";
    public static final String DB_NAME = "citypolice_wz.db";

    public static final String DEFAULT_TIME = "2014-10-30 23:11:02";
    public static final int REQUEST_SIZE = 500;
    public static final int Done_Basic_Dictionary = -1;
    public static final int Done_Basic_JuWeiHui = -2;
    public static final int Done_Basic_PaiChuSuo = -3;
    public static final int Done_Basic_XingZhengQuHua = -4;
    private static int totelDictionary;
    private static int totelPaiChuSuo;
    private static int totelXingZhengQuHua;
    private static int totelJuWeiHui;
    private final String[] checkArr = {Basic_PaiChuSuo, Basic_XingZhengQuHua, Basic_JuWeiHui};
    private static DownloadDbManager mDownloadDbManager;
    private Handler handler;
    private DbManager.DaoConfig daoConfig;
    private DbManager db;
    private String downloadTime;
    private Basic_CheckUpdate_Param.DatasBean bean;
    private List<String> downAbleList;

    public DownloadDbManager(Handler handler) {
        this.handler = handler;
        initDb();
        initData();
    }


//    public static DownloadDbManager getInstance(Handler handler) {
//        initData();
//        if (mDownloadDbManager == null) {
//            synchronized (DownloadDbManager.class) {
//                if (mDownloadDbManager == null) {
//                    mDownloadDbManager = new DownloadDbManager(handler);
//                }
//            }
//        }
//        return mDownloadDbManager;
//    }

    private static void initData() {
        totelDictionary = 0;
        totelPaiChuSuo = 0;
        totelXingZhengQuHua = 0;
        totelJuWeiHui = 0;
    }


    public <T> void downloadDb(String method, final int page, Class<T> clazz, WebServiceCallBack<T> callBack) {
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", REQUEST_SIZE);
        param.put("PageIndex", page);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam("", 0, method, param)
                .setBeanType(clazz)
                .setCallBack(callBack).build();
        PoolManager.getInstance().execute(task);
    }

    /**
     * 开始下载
     */
    public void startDownloadDb() {
        initDb();
        initData();
        downloadDictionary(0);
        checkUpdate();
    }

    /**
     * 下载 Basic_Dictionary
     *
     * @param page
     */
    public void downloadDictionary(final int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("UpdateTime", SharedPreferencesUtils.get(Basic_Dictionary, DEFAULT_TIME));
        param.put("PageSize", REQUEST_SIZE);
        param.put("PageIndex", page);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam("", 0, Basic_Dictionary, param)
                .setBeanType(Basic_Dictionary_Return.class)
                .setCallBack(new WebServiceCallBack<Basic_Dictionary_Return>() {
                    @Override
                    public void onSuccess(Basic_Dictionary_Return bean) {
                        List<Basic_Dictionary_Kj> content = bean.getContent();
                        Log.i(TAG, "Dictionary onSuccess: " + content.size());
                        totelDictionary += content.size();
                        if (content.size() > 0) {
                            saveDate(content);
                            int currentPage = page + 1;
                            downloadDictionary(currentPage);
                        } else {
                            SharedPreferencesUtils.put(Basic_Dictionary, TimeUtil.getFormatTime());
                            Log.i(TAG, "Dictionary 数据库下载: " + totelDictionary);
                            sendMessage(totelDictionary, Done_Basic_Dictionary);

                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    /**
     * 检查派出所，行政规划和居委会数据更新
     */
    public void checkUpdate() {
        Basic_CheckUpdate_Param param = new Basic_CheckUpdate_Param();
        param.setTaskID("1");
        List<Basic_CheckUpdate_Param.DatasBean> datas = new ArrayList<>();
        for (int i = 0; i < checkArr.length; i++) {
            bean = new Basic_CheckUpdate_Param.DatasBean();
            bean.setName(checkArr[i]);
            downloadTime = (String) SharedPreferencesUtils.get(checkArr[i], DEFAULT_TIME);
            bean.setTime(downloadTime);
            datas.add(bean);
        }
        param.setDatas(datas);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam("", 0, "Basic_CheckUpdate", param)
                .setBeanType(Basic_CheckUpdate.class)
                .setCallBack(new WebServiceCallBack<Basic_CheckUpdate>() {

                    @Override
                    public void onSuccess(Basic_CheckUpdate bean) {
                        List<Basic_CheckUpdate.ContentBean> content = bean.getContent();
                        Log.i(TAG, "onSuccess: " + content);
                        downAbleList = new ArrayList<String>();
                        for (Basic_CheckUpdate.ContentBean contentBean : content) {
                            if (contentBean.isIsUpdate()) {
                                downAbleList.add(contentBean.getName());
                            } else {
                                nothingToUpdate(contentBean.getName());
                            }
                        }
                        if (downAbleList.size() > 0) {
                            beginDownload(downAbleList);
                        }

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    /**
     * 三实有不用升级
     *
     * @param name
     */
    private void nothingToUpdate(String name) {
        switch (name) {
            case Basic_PaiChuSuo:
                sendMessage(0, Done_Basic_PaiChuSuo);
                break;
            case Basic_XingZhengQuHua:
                sendMessage(0, Done_Basic_XingZhengQuHua);
                break;
            case Basic_JuWeiHui:
                sendMessage(0, Done_Basic_JuWeiHui);
                break;
            default:
                break;
        }
    }

    /**
     * 开始下载 三实有数据库
     *
     * @param downAbleList
     */
    private void beginDownload(List<String> downAbleList) {
        for (String downAble : downAbleList) {
            switch (downAble) {
                case Basic_PaiChuSuo:
                    downloadPaiChuSuo(0);
                    break;
                case Basic_XingZhengQuHua:
                    downloadXingZhengQuHua(0);
                    break;
                case Basic_JuWeiHui:
                    downloadJuWeiHui(0);
                    break;
                default:
                    break;

            }
        }
    }

    private void sendMessage(int size, int response) {
        Message msg = handler.obtainMessage();
        msg.arg1 = size;
        msg.what = response;
        handler.sendMessage(msg);
    }


    /**
     * 下载派出所
     *
     * @param page
     */
    public void downloadPaiChuSuo(final int page) {
        downloadDb(Basic_PaiChuSuo, page, Basic_PaiChuSuo_Return.class, new WebServiceCallBack<Basic_PaiChuSuo_Return>() {
            @Override
            public void onSuccess(Basic_PaiChuSuo_Return bean) {
                List<Basic_PaiChuSuo_Kj> content = bean.getContent();
                Log.i(TAG, "PaiChuSuo onSuccess: " + content.size());
                totelPaiChuSuo += content.size();
                if (content.size() > 0) {
                    saveDate(content);
                    int currentPage = page + 1;
                    downloadPaiChuSuo(currentPage);
                } else {
                    SharedPreferencesUtils.put(Basic_PaiChuSuo, TimeUtil.getFormatTime());
                    Log.i(TAG, "PaiChuSuo 数据库下载: " + totelPaiChuSuo);
                    sendMessage(totelPaiChuSuo, Done_Basic_PaiChuSuo);
                }
            }

            @Override
            public void onErrorResult(ErrorResult errorResult) {
                Log.i(TAG, "onPaiChuSuoErrorResult: " + errorResult.getResultText());
            }
        });
    }

    /**
     * 下载行政区划
     *
     * @param page
     */
    public void downloadXingZhengQuHua(final int page) {
        downloadDb(Basic_XingZhengQuHua, page, Basic_XingZhengQuHua_Return.class, new WebServiceCallBack<Basic_XingZhengQuHua_Return>() {
            @Override
            public void onSuccess(Basic_XingZhengQuHua_Return bean) {
                List<Basic_XingZhengQuHua_Kj> content = bean.getContent();
                Log.i(TAG, "XingZhengQuHua onSuccess: " + content.size());
                totelXingZhengQuHua += content.size();
                if (content.size() > 0) {
                    saveDate(content);
                    int currentPage = page + 1;
                    downloadXingZhengQuHua(currentPage);
                } else {
                    SharedPreferencesUtils.put(Basic_XingZhengQuHua, TimeUtil.getFormatTime());
                    Log.i(TAG, "XingZhengQuHua 数据库下载: " + totelXingZhengQuHua);
                    sendMessage(totelXingZhengQuHua, Done_Basic_XingZhengQuHua);
                }
            }

            @Override
            public void onErrorResult(ErrorResult errorResult) {
                Log.i(TAG, "onXingZhengQuHuaErrorResult: " + errorResult.getResultText());
            }
        });
    }

    /**
     * 下载居委会
     *
     * @param page
     */
    public void downloadJuWeiHui(final int page) {
        downloadDb(Basic_JuWeiHui, page, Basic_JuWeiHui_Return.class, new WebServiceCallBack<Basic_JuWeiHui_Return>() {
            @Override
            public void onSuccess(Basic_JuWeiHui_Return bean) {
                List<Basic_JuWeiHui_Kj> content = bean.getContent();
                Log.i(TAG, "JuWeiHui onSuccess: " + content.size());
                totelJuWeiHui += content.size();
                if (content.size() > 0) {
                    saveDate(content);
                    int currentPage = page + 1;
                    downloadJuWeiHui(currentPage);
                } else {
                    SharedPreferencesUtils.put(Basic_JuWeiHui, TimeUtil.getFormatTime());
                    sendMessage(totelJuWeiHui, Done_Basic_JuWeiHui);
                    Log.i(TAG, "JuWeiHui 数据库下载: " + totelJuWeiHui);
                    Log.i(TAG, " 数据库下载完成: ");
                }
            }

            @Override
            public void onErrorResult(ErrorResult errorResult) {
                Log.i(TAG, "onJuWeiHuiErrorResult: " + errorResult.getResultText());
            }
        });
    }

    /**
     * 保存数据到数据库
     *
     * @param list
     */
    private <T> void saveDate(final List<T> list) {

        x.task().run(new Runnable() {
            @Override
            public void run() {
                for (T t : list) {
                    try {
                        db.saveOrUpdate(t);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 初始化数据库
     */
    private void initDb() {
        daoConfig = new DbManager.DaoConfig()
//                .setDbDir(Environment.getExternalStorageDirectory())
                .setDbName(DB_NAME)
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                });
        db = x.getDb(daoConfig);
    }
}
